package com.yangyuanming.seckill.enums;

/**
 * Created by Yang Yuanming on 2018/8/18 with IntelliJ IDEA.
 * Description:
 */
public enum SeckillResultEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    SeckillResultEnum(int id, String result) {
        this.id = id;
        this.result = result;
    }

    private int id;
    private String result;

    public int getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public static SeckillResultEnum getSeckillStateEnum(int id){
        for(SeckillResultEnum s: values()){
            if(s.getId()==id){
                return s;
            }
        }
        return null;
    }
}