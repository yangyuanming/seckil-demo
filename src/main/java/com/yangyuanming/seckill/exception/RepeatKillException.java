package com.yangyuanming.seckill.exception;

/**
 * Created by Yang Yuanming on 2018/8/17 with IntelliJ IDEA.
 * Description:重复秒杀异常
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatKillException(String message) {

        super(message);
    }
}