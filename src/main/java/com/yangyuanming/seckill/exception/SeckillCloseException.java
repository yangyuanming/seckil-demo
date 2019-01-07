package com.yangyuanming.seckill.exception;

/**
 * Created by Yang Yuanming on 2018/8/17 with IntelliJ IDEA.
 * Description:
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillCloseException(String message) {

        super(message);
    }
}