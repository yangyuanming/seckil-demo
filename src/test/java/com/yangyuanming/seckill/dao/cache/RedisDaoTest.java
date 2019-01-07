package com.yangyuanming.seckill.dao.cache;

import com.yangyuanming.seckill.dao.SeckillDao;
import com.yangyuanming.seckill.dao.cache.impl.RedisDaoImpl;
import com.yangyuanming.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//junit 启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit  spring的配置文件,spring容器应用该配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
     @Autowired
     private RedisDaoImpl redisDaoImpl;
     @Autowired
     private SeckillDao seckillDao;
     private long seckillId=1000l;
    @Test
    public void testSeckillRedisDao() {
        Seckill seckill=redisDaoImpl.get("seckill:"+seckillId,Seckill.class);
        if(seckill==null){
            seckill= seckillDao.queryById(seckillId);
            if(seckill!=null){
                logger.info("get from mysql seckill{}",seckill);
                redisDaoImpl.put("seckill:"+seckillId,seckill);
            }

        }else{
            logger.info("get from redis seckill{}",seckill);
        }
    }

}