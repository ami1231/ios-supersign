DROP TABLE IF EXISTS `member`;

create table if not exists `member`
(
  id                bigint unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
  account             varchar(64) BINARY          NOT NULL COMMENT '后台账号',
  password              varchar(256)                NOT NULL COMMENT '后台密码',
  create_time         datetime                    DEFAULT CURRENT_TIMESTAMP COMMENT '建立时间',
  UNIQUE KEY (account)
)
comment '使用者';

-- 密碼123456
INSERT INTO `member`(account, password)
values ('admin01', 'e10adc3949ba59abbe56e057f20f883e');