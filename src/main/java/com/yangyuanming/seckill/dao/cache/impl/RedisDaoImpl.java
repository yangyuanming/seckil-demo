package com.yangyuanming.seckill.dao.cache.impl;

import com.yangyuanming.seckill.dao.cache.RedisDao;
import com.yangyuanming.seckill.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Yang Yuanming on 2018/8/21 with IntelliJ IDEA.
 * Description:
 */
public class RedisDaoImpl implements RedisDao {
    private final JedisPool jedisPool;
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    public RedisDaoImpl(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }

    public <T> T get(String key,Class<T> clazz) {
        try {
            Jedis jedis=jedisPool.getResource();

            try {
                /**
                 * 序列化：将对象转化为字节序列
                 * 反序列化：将字节序列转化为对象
                 * Redis和Jedis没有实现序列化的功能
                 * 可以使用java自带的序列化方法，这里使用protostuff:pojo
                 * 做法：protostuff序列化工具实现序列化和反序列化
                 * 读取：get→byte[]→反序列化→对象
                 */
                byte [] data=jedis.get(key.getBytes());
                if(data!=null){
                    T obj=SerializeUtil.deserialize(data,clazz);
                    return obj;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }

            return null;
    }

    public <T> String put(String key,T obj) {

        try {
            Jedis jedis=jedisPool.getResource();
            try {
                byte [] data=SerializeUtil.serialize(obj);
                //超时缓存
                int time=60*60;
                return jedis.setex(key.getBytes(),time,data);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}