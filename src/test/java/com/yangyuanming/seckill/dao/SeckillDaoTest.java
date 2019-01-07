package com.yangyuanming.seckill.dao;

import com.yangyuanming.seckill.entity.Seckill;
import  org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring test，junit4
 */
//junit 启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit  spring的配置文件,spring容器应用该配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        Long id=1000l;
        Seckill seckill=seckillDao.queryById(id);
        System.out.print(seckill);
        /**
         * Seckill{seckillId=1000,
         *          name='2000元秒杀iPhone X',
         *          num=100,
         *          startTime=2018-08-16 00:00:00.0,
         *          endTime=2018-08-17 00:00:00.0,
         *          createTime=2018-08-16 13:27:46.0
         */
    }

    @Test
    public void reduceNumber() {
            long seckillId=1000l;
            int result=seckillDao.reduceNumber(seckillId,new Timestamp(new Date().getTime()));
            System.out.print(result);

    }



    @Test
    public void queryAll() {
        List<Seckill> list= seckillDao.queryAll(0,100);
        for(Seckill iterm:list){
            System.out.println(iterm.toString());
        }
    }
}