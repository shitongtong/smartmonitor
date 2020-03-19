-- CREATE DATABASE `znjk` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- USE znjk;


-- 告警主机群组表
DROP TABLE IF EXISTS `alarm_group`;
CREATE TABLE `alarm_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL COMMENT 'zabbix主机群组名称',
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
  `group_name` varchar(100) NOT NULL COMMENT 'zabbix主机群组名称',
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
  `application_name` varchar(100) NOT NULL COMMENT 'zabbix应用名称',
  `item_key` varchar(1000) NOT NULL COMMENT 'zabbix监控指标key值',
  `item_name` varchar(100) NOT NULL COMMENT '告警指标名称',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警指标表';
INSERT INTO `znjk`.`alarm_item` (`application_name`, `item_key`, `item_name`, `remark`) VALUES ( 'CPU', 'system.cpu.util', 'server.cpu.util', '服务器cpu利用率');
INSERT INTO `znjk`.`alarm_item` (`application_name`, `item_key`, `item_name`, `remark`) VALUES ( 'Status', 'icmpping', 'server.status', '服务器在线状态');
INSERT INTO `znjk`.`alarm_item` (`application_name`, `item_key`, `item_name`, `remark`) VALUES ( 'Status', 'system.uptime', 'server.uptime', '服务器运行时间');


-- 性能数据表
DROP TABLE IF EXISTS `item_data`;
CREATE TABLE `item_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(22) NOT NULL COMMENT 'ip',
  `port` varchar(22) NOT NULL DEFAULT '' COMMENT '端口',
  `name` varchar(22) NOT NULL COMMENT '告警指标名称',
  `value` varchar(22) NOT NULL DEFAULT '' COMMENT '告警指标值',
  `units` varchar(22) NOT NULL DEFAULT '' COMMENT '告警指标值的单位',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_data` (`ip`,`name`,`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='性能数据表';

-- 性能数据历史表
DROP TABLE IF EXISTS `item_data_history`;
CREATE TABLE `item_data_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(22) NOT NULL COMMENT 'ip',
  `port` varchar(22) NOT NULL DEFAULT '' COMMENT '端口',
  `name` varchar(22) NOT NULL COMMENT '告警指标名称',
  `value` varchar(22) NOT NULL DEFAULT '' COMMENT '告警指标值',
  `units` varchar(22) NOT NULL DEFAULT '' COMMENT '告警指标值的单位',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='性能数据历史表';


-- 告警指标设置表
DROP TABLE IF EXISTS `alarm_performance`;
CREATE TABLE `alarm_performance` (
  `id` int(11) NOT NULL COMMENT 'id',
  `eqp_type` int(11) DEFAULT NULL COMMENT '设备类型:资源库表设备类型ID',
  `name` varchar(22) NOT NULL COMMENT '性能指标名称(中文)',
  `item_name` varchar(22) NOT NULL COMMENT '告警指标名称(英文)',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `is_threshold` int(2) DEFAULT NULL COMMENT '是否需要设置阈值',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_name` (`item_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警指标设置表';


-- 告警规则表
DROP TABLE IF EXISTS `alarm_rules`;
CREATE TABLE `alarm_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_name` varchar(255) NOT NULL COMMENT '规则名称',
  `res_type` int(11) DEFAULT NULL COMMENT '资源一级ID',
  `res_type_name` varchar(50) DEFAULT NULL COMMENT '父级资源类型名称',
  `eqp_type` int(11) DEFAULT NULL COMMENT '设备类型:资源库表设备类型ID',
  `eqp_type_name` varchar(50) DEFAULT NULL COMMENT '设备类型名称',
  `performance_id` int(11) NOT NULL COMMENT '性能指标表alarm_performance的id',
  `performance_name` varchar(22) DEFAULT NULL COMMENT '性能指标名称',
  `item_name` varchar(22) NOT NULL COMMENT '告警指标名称(英文)',
  `threshold_status` int(2) NOT NULL DEFAULT '0' COMMENT '是否需要设置阈值:0不需要; 1:需要',
  `lower_threshold` int(11) NOT NULL DEFAULT '0' COMMENT '阈值(下限)',
  `threshold` int(11) DEFAULT NULL COMMENT '阈值(上限)',
  `alarm_level` int(2) NOT NULL COMMENT '告警级别 0:一级/A/重大故障; 1:二级/B/严重故障; 2:三级/C/一般故障',
  `send_status` int(2) NOT NULL COMMENT '是否派单 0:否; 1:是',
  `use_status` int(2) NOT NULL COMMENT '启用状态 0:否; 1:是',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `recent_time` datetime DEFAULT NULL COMMENT '最近调用时间',
  `description` varchar(1000) DEFAULT NULL COMMENT '规则描述',
  `alarm_count` int(11) NOT NULL DEFAULT '1' COMMENT '发送活跃告警条件:连续检测到告警次数',
  `clear_count` int(11) NOT NULL DEFAULT '1' COMMENT '发送清除告警条件:连续检测到清除告警次数',
  `remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '通用备注',
  `sort` int(8) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态: 0:删除; 1:正常;',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_name` (`item_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警规则表';


-- 告警表
DROP TABLE IF EXISTS `alarm_data`;
CREATE TABLE `alarm_data` (
  `alarm_id` varchar(50) NOT NULL COMMENT '告警唯一标识',
  `clear_id` varchar(50) DEFAULT NULL COMMENT '告警清除标识',
  `ip` varchar(50) DEFAULT NULL COMMENT '设备IP',
  `global_code` varchar(100) DEFAULT NULL COMMENT '全局编码',
  `item_name` varchar(22) DEFAULT NULL COMMENT '告警指标名称',
  `res_type` int(11) DEFAULT NULL COMMENT '资源类别:资源库表类别ID',
  `res_type_name` varchar(255) DEFAULT NULL COMMENT '资源类别名称',
  `eqp_type` int(11) DEFAULT NULL COMMENT '设备类型:资源库表设备类型ID',
  `eqp_type_name` varchar(255) DEFAULT NULL COMMENT '设备类型名称',
  `fault_type` varchar(255) DEFAULT NULL COMMENT '故障类型',
  `cracking_type` varchar(255) DEFAULT NULL COMMENT '劣化类型',
  `alarm_status` int(2) NOT NULL COMMENT '告警状态: 0:活动告警; 1:清除告警;',
  `event_time` datetime DEFAULT NULL COMMENT '告警发生时间',
  `clear_time` datetime DEFAULT NULL COMMENT '告警清除时间',
  `alarm_type` int(11) DEFAULT NULL COMMENT '告警类别:见代码枚举类',
  `alarm_source` int(2) DEFAULT '1' COMMENT '告警来源: 1:自告警; 2:大华; 3:韬安; 4:敦荣; 5:丰盈',
  `alarm_level` int(2) DEFAULT NULL COMMENT '告警级别 0:A(重大故障); 1:B(严重故障) 2:C(一般故障)',
  `alarm_title` varchar(200) DEFAULT NULL COMMENT '告警标题',
  `alarm_title_type` int(2) DEFAULT NULL COMMENT '告警标题类型:暂废弃',
  `alarm_text` varchar(2000) DEFAULT NULL COMMENT '告警内容',
  `alarm_text_type` int(2) DEFAULT NULL COMMENT '告警内容类型:暂废弃',
  `alarm_image` varchar(255) DEFAULT NULL COMMENT '告警图片',
  `facility_name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `facility_frim` varchar(255) DEFAULT NULL COMMENT '设备厂商',
  `eqp_code` varchar(100) DEFAULT NULL COMMENT '设备编码',
  `asset_number` varchar(255) NOT NULL DEFAULT '' COMMENT '资产编号',
  `police_id` int(11) DEFAULT NULL COMMENT '派出所ID',
  `police_station` varchar(255) DEFAULT NULL COMMENT '派出所',
  `location` varchar(1000) DEFAULT NULL COMMENT '受影响点位',
  `mac` varchar(100) DEFAULT NULL COMMENT 'mac地址',
  `port_code` varchar(100) DEFAULT NULL COMMENT '端口编号',
  `diagnose_result` varchar(255) DEFAULT NULL COMMENT '诊断结果 暂废弃',
  `plan_type` int(2) DEFAULT '0' COMMENT '暂废弃',
  `parent_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '父告警ID 暂废弃',
  `ack_status` int(2) DEFAULT '0' COMMENT '告警是否确认：0:未确认;1:已确认 暂废弃',
  `confirm_time` datetime DEFAULT NULL COMMENT '告警确认时间 暂废弃',
  `confirm_user` varchar(22) DEFAULT NULL COMMENT '告警确认人 暂废弃',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`alarm_id`),
  UNIQUE KEY `uk_alarm_data` (`ip`,`item_name`,`port_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警表';


-- 告警工单表
DROP TABLE IF EXISTS `alarm_order`;
CREATE TABLE `alarm_order` (
  `alarm_id` varchar(50) NOT NULL COMMENT '告警唯一标识',
  `order_no` varchar(100) DEFAULT NULL COMMENT '工单号',
  `send_status` int(2) DEFAULT NULL COMMENT '派单状态 0:派单失败; 1:已派单; 2:已结单',
  `send_result` varchar(1000) DEFAULT NULL COMMENT '派单结果',
  `order_status` int(2) DEFAULT NULL COMMENT '工单状态: 0:报修; 1:派单; 2:接单; 3:处理完成; 4:已评价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警工单表';