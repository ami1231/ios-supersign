create table if not exists device
(
	id int auto_increment primary key,
	udid varchar(255) not null comment '设备UDID',
	apple_id int not null comment '此设备所使用的帐号id',
	device_id varchar(255) not null comment '设备id',
	creat_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间'
)
comment '设备';