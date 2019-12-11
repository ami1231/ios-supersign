create table if not exists apple_account
(
	id int auto_increment primary key,
	account varchar(255) null comment 'apple开发者帐号',
	`count` int not null comment '已有设备数量',
	p8 text not null comment '私钥',
	issuer_id varchar(500) not null comment '在Store Connect上可以点击复制 iss ID',
	kid text not null comment 'your own 金鑰 ID',
	p12 varchar(255) null comment 'p12文件地址',
	cer_id varchar(255) not null comment '授权证书id',
	bundle_ids varchar(255) not null comment '开发者后台的通配证书id',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '帐号添加时间'
)
comment '帐号';