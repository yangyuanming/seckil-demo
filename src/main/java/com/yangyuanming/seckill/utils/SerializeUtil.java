package com.yangyuanming.seckill.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.DefaultIdStrategy;
import com.dyuproject.protostuff.runtime.Delegate;
import com.dyuproject.protostuff.runtime.RuntimeEnv;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.yangyuanming.seckill.other.TimestampDelegate;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yang Yuanming on 2018/8/21 with IntelliJ IDEA.
 * Description:使用protostuff进行序列化和反序列化的工具包
 */
public class SerializeUtil {
    ///时间戳转换Delegate，解决时间戳转换后错误问题
    private final static Delegate<Timestamp> TIMESTAMP_DELEGATE = new TimestampDelegate();

    private final static DefaultIdStrategy idStrategy     = ((DefaultIdStrategy) RuntimeEnv.ID_STRATEGY);

    private final static ConcurrentHashMap<Class<?>, Schema<?>> cachedSchema    = new ConcurrentHashMap<Class<?>, Schema<?>>();

    static {
        idStrategy.registerDelegate(TIMESTAMP_DELEGATE);
    }

    public SerializeUtil() {

    }

    public static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz, idStrategy);
            cachedSchema.put(clazz, schema);
        }
        return schema;
    }

    public static  <T> byte[] serialize(T  obj){
        if (obj == null) {
            return null;
        }
        Class<T> cls = (Class<T>) obj.getClass();
        //缓存区
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
            return bytes;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }

    }



    public static <T> T deserialize(byte [] bytes, Class<T> clazz){
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            Schema<T> schema = getSchema(clazz);
            //改为由Schema来实例化解码对象，没有构造函数也没有问题
            T message = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

}