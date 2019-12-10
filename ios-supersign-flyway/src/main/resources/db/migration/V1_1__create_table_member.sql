DROP TABLE IF EXISTS `member`;

create table if not exists `member`
(
  id                bigint unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
  account             varchar(64) BINARY          NOT NULL COMMENT '后台账号',
  passwd              varchar(256)                NOT NULL COMMENT '后台密码',
  create_time         datetime                    DEFAULT CURRENT_TIMESTAMP COMMENT '建立时间',
  UNIQUE KEY (account)
)
comment '使用者';

-- 密碼123456
INSERT INTO `member`(account, passwd)
values ('admin01', '$2a$10$2fcjZfkLYsCQ/IrgC0iLhe7cZghun.TlZXbsUKTQEI1dUDqCriJVq');