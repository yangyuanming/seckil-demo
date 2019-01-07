package com.yangyuanming.seckill.service.impl;

import com.yangyuanming.seckill.dao.SeckillDao;
import com.yangyuanming.seckill.dao.SuccessKilledDao;
import com.yangyuanming.seckill.dao.cache.impl.RedisDaoImpl;
import com.yangyuanming.seckill.dto.Exposer;
import com.yangyuanming.seckill.entity.Seckill;
import com.yangyuanming.seckill.entity.SuccessKilled;
import com.yangyuanming.seckill.exception.RepeatKillException;
import com.yangyuanming.seckill.exception.SeckillCloseException;
import com.yangyuanming.seckill.exception.SeckillException;
import com.yangyuanming.seckill.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Yuanming on 2018/8/17 with IntelliJ IDEA.
 * Description:
 */

@Service("seckillServiceImpl")
public class SeckillServiceImpl implements SeckillService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
   @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDaoImpl redisDaoImpl;


    //MD5盐值字符串 用于混淆MD5
    private final String salt="ddsdfsdgfcxnckj5648g9w42634#@$@#^#&^!@#[[{fsdfsHDS^&#%^}";

    public List<Seckill> getSeckillList(int offset,int limit) {
        return seckillDao.queryAll(offset,limit);
    }

    public int executeSeckillWithProcedure(long seckillId, long userPhone, String md5){
        if(md5==null||!md5.equals(getMD5(seckillId))){
            return -3;
        }
        Map<String,Object> map=new HashMap<String,Object>();
        Timestamp seckillTime=new Timestamp(new Date().getTime());
        map.put("seckillId",seckillId);
        map.put("userPhone",userPhone);
        map.put("seckillTime",seckillTime);
        map.put("result",null);
        int result=-2;
        try {
            seckillDao.seckillWithProcedure(map);
            result=MapUtils.getInteger(map,"result",-2);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return -2;
        }

        return result;
    }

    public SuccessKilled getSuccessKilledBySeckillIdAndPhone(long seckillId,long userPhone){
        return  successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
    }

    public Seckill getById(long seckillId) {
        //从redis缓存中查找数据
        Seckill seckill=redisDaoImpl.get("seckill:"+seckillId,Seckill.class);
        logger.info("get from redis {}",seckill);
        if(seckill==null){
            //如果数据为null，则从数据库中查
            seckill= seckillDao.queryById(seckillId);
            if(seckill!=null){
                //将查出的数据放到redis缓存中
                redisDaoImpl.put("seckill:"+seckillId,seckill);
            }

        }
        return seckill;
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //从redis缓存中查找数据
        Seckill seckill=redisDaoImpl.get("seckill:"+seckillId,Seckill.class);
        logger.info("get from redis {}",seckill);
        if(seckill==null){
            //如果数据为null，则从数据库中查
            seckill= seckillDao.queryById(seckillId);
            if(seckill!=null){
                //将查出的数据放到redis缓存中
                redisDaoImpl.put("seckill:"+seckillId,seckill);
            }else{
                return new Exposer(false,seckillId);
            }

        }
        Date start=seckill.getStartTime();
        Date end =seckill.getEndTime();
        Date now=new Date();
        if(now.getTime()<start.getTime()||now.getTime()>end.getTime()){

            return new Exposer(false,seckillId,now.getTime(),start.getTime(),end.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    //md5加密
    private String getMD5(long seckillId){
        String base=seckillId+"/"+salt;
        String md5=DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    @Transactional
    public SuccessKilled executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException,SeckillCloseException,SeckillException  {
             if(md5==null||!md5.equals(getMD5(seckillId))){
                 throw new SeckillException("seckill data rewrite");
             }
             //执行秒杀逻辑，先插入购买明细再去减库存，可以减少减库存带来的行级锁占用时间。

        try {
            //插入购买记录
            int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
            if(insertCount<1){
                throw new RepeatKillException("seckill repeated");
            }else{
                int updateCount=0;
                //减库存
                updateCount=seckillDao.reduceNumber(seckillId,new Timestamp(new Date().getTime()));
                if(updateCount<1){
                    throw new  SeckillCloseException("seckill is closed");
                }
                return  successKilledDao.queryByIdWithSeckill(seckillId,userPhone);

            }
        } catch(RepeatKillException rke) {
                 throw rke;

        } catch(SeckillCloseException sce){
                 throw sce;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //转化为运行期异常 rollback
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }


    }
}