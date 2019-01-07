package com.yangyuanming.seckill.entity;

import java.sql.Timestamp;

/**
 * Created by Yang Yuanming on 2018/8/16 with IntelliJ IDEA.
 * Description:
 */
public class SuccessKilled {

    private long seckillId;
    private long userPhone;
    private Timestamp createTime;
    private short state;
    private Seckill seckill;

    public Seckill getSeckill() {
        return seckill;
    }


    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", createTime=" + createTime +
                ", state=" + state +
                '}';
    }
}