package com.yangyuanming.seckill.dao;

import com.yangyuanming.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

//junit 启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit  spring的配置文件,spring容器应用该配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() {
        int result=successKilledDao.insertSuccessKilled(1000l,15619130264l);
        System.out.println(result);
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(1000l,15629130294l);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }


}