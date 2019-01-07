package com.yangyuanming.seckill.service;

import com.yangyuanming.seckill.dto.Exposer;
import com.yangyuanming.seckill.entity.Seckill;
import com.yangyuanming.seckill.entity.SuccessKilled;
import com.yangyuanming.seckill.exception.RepeatKillException;
import com.yangyuanming.seckill.exception.SeckillCloseException;
import com.yangyuanming.seckill.exception.SeckillException;

import java.util.List;

/**
 * 站在 使用者 角度设计接口
 * Created by Yang Yuanming on 2018/8/17 with IntelliJ IDEA.
 * Description:
 */
public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
     List<Seckill> getSeckillList(int offset,int limit);
    /**
     * 查询某个秒杀商品
     */
     Seckill getById(long seckillId);

    /**
     * 秒杀开启则返回秒杀接口，否则返回系统时间和秒杀时间
     * @param seckillId
     * @return
     */
     Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SuccessKilled executeSeckill(long seckillId, long userPhone , String md5)
      throws RepeatKillException,SeckillCloseException,SeckillException;

    /**
     * 执行秒杀操作 通过存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    int executeSeckillWithProcedure(long seckillId, long userPhone , String md5);

    SuccessKilled getSuccessKilledBySeckillIdAndPhone(long seckillId,long userPhone);
}
