package com.yangyuanming.seckill.enums;

/**
 * Created by Yang Yuanming on 2018/8/19 with IntelliJ IDEA.
 * Description:
 */
public enum CodeEnum {
    OK(200,"成功"),
    FORBIDDEN(403,"失败"),
    UNAUTHORIZED(401,"请登录");

    CodeEnum(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static CodeEnum getCodeEnum(int code){
        for(CodeEnum ce:values()){
            if(ce.getCode()==code){
                return ce;
            }
        }
        return null;
    }
}