package com.yangyuanming.seckill.dao;

import com.yangyuanming.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Yang Yuanming on 2018/8/16 with IntelliJ IDEA.
 * Description:
 */
public interface SuccessKilledDao {
    /**
     * 插入秒杀记录
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

    /**
     *根据用户手机号和秒杀商品的id查询对应的秒杀记录，返回时带上秒杀商品的详细信息
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);

}
