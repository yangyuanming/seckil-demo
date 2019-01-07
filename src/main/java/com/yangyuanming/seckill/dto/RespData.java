package com.yangyuanming.seckill.dto;

import java.util.HashMap;
import java.util.Map;

public class RespData {

    private  String msg;
    private  final int code;
    private  Map<String, Object> data = new HashMap<String, Object>();

    public RespData(int code,String msg) {
        this.code = code;
        this.msg=msg;
    }
    public RespData(int code) {
        this.code=code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public RespData putDataValue(String key, Object value) {
        data.put(key, value);
        return this;
    }

    //表示操作成功
    public static RespData ok() {
        return new RespData(200);
    }
    //自定义返回的代码和消息
    public static RespData customerError(int code,String msg) {
        return new RespData(code, msg);
    }
    //表示认证过期，权限不足
    public static RespData unauthorized() {
        return new RespData(401);

    }

    //表示服务器理解了请求，但拒绝处理或者处理过程中存在异常，操作失败
    public static RespData forbidden() {
        return new RespData(403);
    }
    /*
    public static RespData notFound() {
        return new RespData(404);
    }

    public static RespData badRequest() {
        return new RespData(400);
    }

    public static RespData serverInternalError() {
        return new RespData(500);
    }

    */

}
