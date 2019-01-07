package com.yangyuanming.seckill.service.impl;

import com.yangyuanming.seckill.dto.Exposer;
import com.yangyuanming.seckill.entity.Seckill;
import com.yangyuanming.seckill.entity.SuccessKilled;
import com.yangyuanming.seckill.enums.SeckillResultEnum;
import com.yangyuanming.seckill.exception.RepeatKillException;
import com.yangyuanming.seckill.exception.SeckillCloseException;
import com.yangyuanming.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


//junit 启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit  spring的配置文件,spring容器应用该配置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})

public class SeckillServiceImplTest {
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list=seckillService.getSeckillList(0,4);
        //{}是占位符
        logger.info("list={}",list);
    }

    @Test
    public void getById() {
        Seckill seckill=seckillService.getById(1000);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testSeckill() {
        Exposer exposer=seckillService.exportSeckillUrl(1001l);
        logger.info("exposer={}",exposer);
        if(exposer.isExposed()==true){
            try {
                SuccessKilled successKilled =seckillService.executeSeckill(exposer.getSeckillId(),15996880299l,exposer.getMd5());
                logger.info("SuccessKilled={}",successKilled);
            } catch (RepeatKillException re) {
                logger.error(re.getMessage());
            }catch (SeckillCloseException sce){
                logger.error(sce.getMessage());
            }
        }

    }
    @Test
    public void testSeckillWithProcedure(){
        Exposer exposer=seckillService.exportSeckillUrl(1001l);
        logger.info("exposer={}",exposer);
        if(exposer.isExposed()==true){
            int result =seckillService.executeSeckillWithProcedure(exposer.getSeckillId(),15696499987l,exposer.getMd5());
            logger.info("Execute seckill with procedure,the result info "+SeckillResultEnum.getSeckillStateEnum(result).getResult());
        }
    }

}