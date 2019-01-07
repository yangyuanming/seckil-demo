package com.yangyuanming.seckill.dao;

import com.yangyuanming.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Yuanming on 2018/8/16 with IntelliJ IDEA.
 * Description:
 */
public interface SeckillDao {
    /**
     * 秒杀减库存
     * @param seckillId
     * @param seckillTime
     * @return
     */
    int reduceNumber(@Param("seckillId")long seckillId, @Param("seckillTime")Timestamp seckillTime);

    /**
     *根据id查询秒杀商品
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量和个数查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    //java不会保存形参，queryAll(int offset,int limit)------>queryAll(arg0,arg1),需要用@Param说明形参
    List<Seckill> queryAll(@Param("offset")int offset, @Param("limit") int limit);

    void seckillWithProcedure(Map<String,Object> paramMap);

}
