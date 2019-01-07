DROP DATABASE  if exists seckill;
CREATE  DATABASE seckill;
use seckill;
  CREATE TABLE seckill(
    `seckill_id` bigint  NOT NULL AUTO_INCREMENT COMMENT '库存商品id',
    `name` varchar(120) NOT NULL COMMENT '商品名称',
    `number` int NOT NULL COMMENT '库存数量',
		`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `start_time` datetime  NOT NULL COMMENT '开始时间',
    `end_time` datetime   NOT NULL  COMMENT '秒杀结束时间',
    primary key(seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
 )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

insert into
  seckill(name,number,start_time,end_time)
values
  ('2000元秒杀iPhone X',100,'2018-08-16 00:00:00','2018-08-17 00:00:00'),
  ('1000元秒杀iwatch',300,'2018-08-16 00:00:00','2018-08-17 00:00:00'),
  ('500元秒杀荣耀8',200,'2018-08-16 00:00:00','2018-08-17 00:00:00');

create table success_killed(
  `seckill_id` bigint not null comment '秒杀商品id',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_phone` bigint not null comment '用户手机号',
  `state`  tinyint not null  default 0 comment '-1:无效  0：成功  1：已付款 2：已发货',
  primary key(seckill_id,user_phone),
  foreign key(seckill_id)
    references  seckill(seckill_id)
    on update cascade  on delete cascade ,
  key idx_create_time(create_time)

)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

