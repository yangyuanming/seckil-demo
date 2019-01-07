-- 创建秒杀存储过程
-- in ： 输入参数 out ： 输出参数
-- row_count() 返回上一条修改类型的sql执行后的影响行数
--row_count()   0:已经执行，但没修改数据  >0:成功修改的行数 <0:错误或sql没有执行

DELIMITER $  -- console的结束符号 ; 改为 $

CREATE PROCEDURE `seckill`.`execute_seckill`
(in v_seckill_id bigint ,in v_user_phone bigint,in v_seckill_time datetime ,out r_result int)
begin
   declare row_count int default 0;
   start transaction ;
   insert ignore into success_killed(seckill_id,user_phone,create_time)
   values(v_seckill_id,v_user_phone,v_seckill_time);
   set row_count =(select row_count());
   if(row_count=0) then
      rollback ;
      set r_result=-1;
   elseif(row_count<0) then
      rollback ;
      set r_result =-2;
   else
      update seckill
      set number=number-1
      where seckill_id=v_seckill_id
      and number>0
      and start_time<v_seckill_time
      and end_time>v_seckill_time;
      set row_count =(select row_count ());
      if(row_count=0) then
          rollback ;
          set r_result=0;
       elseif(row_count<0) then
          rollback ;
          set r_result =-2;
       else
          commit;
          set r_result=1;
       end if;
    end if;
end;
$
-- 存储过程定义完成
-- 结束符改回 ;
DELIMITER ;

set @v_result=-3;
-- 执行存储过程
call execute_seckill(1000,15623654289,now(),@v_result);
-- 获取结果
select @v_result;
/*
1、使用存储过程，事务行级锁持有时间优化
2、不要过度依赖存储过程。存储过程在银行用的多，结合DB2或oracle
3、简单的逻辑，可以应用存储过程。
4、一个秒杀单6000/qps
 */