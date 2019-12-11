create table if not exists ipa_package
(
	id int auto_increment primary key,
	`name` varchar(50) not null comment '包名',
	icon varchar(255) comment '图标 url',
	version varchar(30) comment '版本',
	build_version varchar(30) not null comment '编译版本号',
	mini_version varchar(30) not null comment '最小支持版本',
	bundle_identifier varchar(255) not null comment '安装包id',
	link varchar(255) not null comment '下载地址',
	mobileconfig varchar(255) null comment '获取UDID证书名称',
	summary varchar(255) null comment '简介',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	`count` int default 0 not null comment '总下载量',
	UNIQUE KEY (bundle_identifier),
	UNIQUE KEY (link)
)
comment 'ipa安装包';