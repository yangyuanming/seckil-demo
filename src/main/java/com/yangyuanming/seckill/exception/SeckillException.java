package com.yangyuanming.seckill.exception;

/**
 * Created by Yang Yuanming on 2018/8/17 with IntelliJ IDEA.
 * Description:
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(String message) {

        super(message);
    }
}