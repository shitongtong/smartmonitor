-- CREATE DATABASE `znjk` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- USE znjk;


-- 告警主机群组表
DROP TABLE IF EXISTS `alarm_group`;
CREATE TABLE `alarm_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL COMMENT 'zabbix主机群组名称',
  `group_id` varchar(100) NOT NULL DEFAULT '' COMMENT 'zabbix主机群组Id',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警主机群组表';


-- 告警应用表
DROP TABLE IF EXISTS `alarm_application`;
CREATE TABLE `alarm_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_group_id` int(11) NOT NULL COMMENT '表alarm_group主键id',
  `application_name` varchar(100) NOT NULL COMMENT 'zabbix应用名称',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警应用表';


-- 告警指标表
DROP TABLE IF EXISTS `alarm_item`;
CREATE TABLE `alarm_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_application_id` int(11) NOT NULL COMMENT '表alarm_application主键id',
  `item_key` varchar(1000) NOT NULL COMMENT 'zabbix监控指标key值',
  `item_name` varchar(100) NOT NULL COMMENT '告警指标名称',
  `item_type` varchar(100) NOT NULL COMMENT '指标类型,做告警类型区分用',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警指标表';