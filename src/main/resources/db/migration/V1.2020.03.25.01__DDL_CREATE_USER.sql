-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                   bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`             varchar(64)         NOT NULL COMMENT '用户名',
    `nickname`             varchar(64)         NOT NULL COMMENT '用户昵称',
    `mobile`               char(11)            NOT NULL COMMENT '用户手机',
    `password`             varchar(255)        NOT NULL COMMENT '用户密码',
    `is_enabled`           tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否启用',
    `is_locked`            tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否锁定',
    `is_expired`           tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否过期',
    `is_password_expired`  tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '密码是否过期',
    `is_deleted`           tinyint(1) unsigned NOT NULL default '0' COMMENT '是否删除',
    `create_time`          datetime            NOT NULL COMMENT '创建时间',
    `update_time`          datetime            NOT NULL COMMENT '更新时间',
    `password_update_time` datetime            NOT NULL COMMENT '密码更新时间',
    `create_user`          bigint(20) unsigned NOT NULL default '0' COMMENT '创建用户',
    `update_user`          bigint(20) unsigned NOT NULL default '0' COMMENT '更新用户',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY `idx_is_enabled` (`is_enabled`) USING BTREE,
    KEY `idx_is_locked` (`is_locked`) USING BTREE,
    KEY `idx_is_expired` (`is_expired`) USING BTREE,
    KEY `idx_is_password_expired` (`is_password_expired`) USING BTREE,
    KEY `idx_is_deleted` (`is_deleted`) USING BTREE,
    KEY `idx_nickname` (`nickname`) USING BTREE,
    KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';
