package com.yangyuanming.seckill.dao.cache;

public interface RedisDao{
    public  <T> T  get(String key,Class<T> clazz);
    public <T> String put(String key,T obj);
}

///**
// * Created by Yang Yuanming on 2018/8/20 with IntelliJ IDEA.
// * Description:
// */
//public class RedisDao {
//    private final JedisPool jedisPool;
//    private final Logger logger=LoggerFactory.getLogger(this.getClass());
//    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
//
//    public RedisDao(String ip,int port){
//        jedisPool=new JedisPool(ip,port);
//    }
//
//    public Seckill getSeckill(long seckillId){
//
//        try {
//            Jedis jedis=jedisPool.getResource();
//
//            try {
//                /**
//                 * 序列化：将对象转化为字节序列
//                 * 反序列化：将字节序列转化为对象
//                 * Redis和Jedis没有实现序列化的功能
//                 * 可以使用java自带的序列化方法，这里使用protostuff:pojo
//                 * 做法：protostuff序列化工具实现序列化到redis
//                 * 读取：get→byte[]→反序列化→对象
//                 */
//                String  key="seckill:"+seckillId;
//                byte [] data=jedis.get(key.getBytes());
//                if(data!=null){
//                    Seckill seckill=schema.newMessage();
//                    ProtostuffIOUtil.mergeFrom(data,seckill,schema);
//                    return seckill;
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            } finally {
//                jedis.close();
//            }
//        } catch (Exception e) {
//           logger.error(e.getMessage(),e);
//        }
//
//            return null;
//    }
//
//    public String putSeckill(Seckill seckill){
//        //序列化
//        try {
//            Jedis jedis=jedisPool.getResource();
//            try {
//                String key="seckill:"+seckill.getSeckillId();
//                byte [] data=ProtostuffIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
//                //超时缓存
//                int time=60*60;
//                return jedis.setex(key.getBytes(),time,data);
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            } finally {
//                jedis.close();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        return null;
//    }
//}