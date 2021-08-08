-- --------------------------------------------------------
-- 主机:                           rm-bp1ka47q1c925092xyo.mysql.rds.aliyuncs.com
-- 服务器版本:                        5.7.28-log - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 village 的数据库结构
DROP DATABASE IF EXISTS `village`;
CREATE DATABASE IF NOT EXISTS `village` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `village`;

-- 导出  表 village.app_member 结构
DROP TABLE IF EXISTS `app_member`;
CREATE TABLE IF NOT EXISTS `app_member` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `platform` tinyint(1) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `vip_end_time` datetime(6) DEFAULT NULL,
  `vip_start_time` datetime(6) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `vip_status` tinyint(1) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL,
  `grade` int(11) unsigned DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `invite_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `invite_code` (`invite_code`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.app_member 的数据：~2 rows (大约)
DELETE FROM `app_member`;
/*!40000 ALTER TABLE `app_member` DISABLE KEYS */;
INSERT INTO `app_member` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `avatar`, `email`, `mobile`, `nickname`, `password`, `platform`, `sex`, `status`, `type`, `username`, `vip_end_time`, `vip_start_time`, `permissions`, `vip_status`, `birth`, `address`, `invite_code`, `grade`, `position`, `invite_by`, `description`) VALUES
	(255227305549369344, '', '2020-03-25 21:14:32.000000', 0, 'admin', '2020-04-27 17:20:45.307000', 'https://ooo.0o0.ooo/2020/04/18/NmF3IP4TOoVbLf5.png', '', '18782059031', '187****9031', NULL, -1, '', 0, 1, '1251014922381430800', '2030-04-30 00:00:00.000000', '2020-04-01 00:00:00.000000', 'MEMBER', 1, NULL, '中国 北京市 北京市 朝阳区 酒仙桥路 3号 电子城．国际电子总部', '12N3VE6CK440G', 0, '30.5681733200,104.0666198700', '', NULL),
	(1251783645962833920, 'admin', '2020-04-19 16:04:12.000000', 0, 'admin', '2020-04-27 17:24:23.091000', 'https://ooo.0o0.ooo/2020/04/18/NmF3IP4TOoVbLf5.png', '', '18782059092', '187****9092', NULL, 1, '', 0, 1, '1251783646004776960', '2020-04-07 00:00:00.000000', '2020-04-01 00:00:00.000000', 'MEMBER', 2, NULL, '中国 北京市 北京市 朝阳区 酒仙桥路 3号 电子城．国际电子总部', '12NPQJ01S4400', 0, '30.5681733200,104.0666198700', '1251014922381430800', NULL),
	(1251783729291071489, 'admin', '2020-04-19 16:04:32.000000', 0, 'admin', '2020-04-27 22:03:48.509000', 'https://ooo.0o0.ooo/2020/04/18/NmF3IP4TOoVbLf5.png', '', '18782059033', '187****9093', NULL, 2, '', 0, 0, '1251783729303654400', NULL, NULL, 'MEMBER', 0, NULL, '中国 北京市 北京市 朝阳区 酒仙桥路 3号 电子城．国际电子总部', '12NPQLDKC4400', 0, '30.5681733200,104.0666198700', '1251014922381430800', NULL);
/*!40000 ALTER TABLE `app_member` ENABLE KEYS */;

-- 导出  表 village.qrtz_blob_triggers 结构
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_blob_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_blob_triggers`;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;

-- 导出  表 village.qrtz_calendars 结构
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE IF NOT EXISTS `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_calendars 的数据：~0 rows (大约)
DELETE FROM `qrtz_calendars`;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;

-- 导出  表 village.qrtz_cron_triggers 结构
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_cron_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_cron_triggers`;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;

-- 导出  表 village.qrtz_fired_triggers 结构
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_fired_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_fired_triggers`;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;

-- 导出  表 village.qrtz_job_details 结构
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE IF NOT EXISTS `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_job_details 的数据：~0 rows (大约)
DELETE FROM `qrtz_job_details`;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;

-- 导出  表 village.qrtz_locks 结构
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE IF NOT EXISTS `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_locks 的数据：~0 rows (大约)
DELETE FROM `qrtz_locks`;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES
	('quartzScheduler', 'TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;

-- 导出  表 village.qrtz_paused_trigger_grps 结构
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE IF NOT EXISTS `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_paused_trigger_grps 的数据：~0 rows (大约)
DELETE FROM `qrtz_paused_trigger_grps`;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;

-- 导出  表 village.qrtz_scheduler_state 结构
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE IF NOT EXISTS `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_scheduler_state 的数据：~0 rows (大约)
DELETE FROM `qrtz_scheduler_state`;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;

-- 导出  表 village.qrtz_simple_triggers 结构
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_simple_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_simple_triggers`;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;

-- 导出  表 village.qrtz_simprop_triggers 结构
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_simprop_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_simprop_triggers`;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;

-- 导出  表 village.qrtz_triggers 结构
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE IF NOT EXISTS `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.qrtz_triggers 的数据：~0 rows (大约)
DELETE FROM `qrtz_triggers`;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;

-- 导出  表 village.t_act_business 结构
DROP TABLE IF EXISTS `t_act_business`;
CREATE TABLE IF NOT EXISTS `t_act_business` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `proc_def_id` varchar(255) DEFAULT NULL,
  `proc_inst_id` varchar(255) DEFAULT NULL,
  `result` tinyint(1) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `table_id` bigint(20) unsigned DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_business 的数据：~0 rows (大约)
DELETE FROM `t_act_business`;
/*!40000 ALTER TABLE `t_act_business` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_business` ENABLE KEYS */;

-- 导出  表 village.t_act_category 结构
DROP TABLE IF EXISTS `t_act_category`;
CREATE TABLE IF NOT EXISTS `t_act_category` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  `parent_id` bigint(20) unsigned NOT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_category 的数据：~0 rows (大约)
DELETE FROM `t_act_category`;
/*!40000 ALTER TABLE `t_act_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_category` ENABLE KEYS */;

-- 导出  表 village.t_act_model 结构
DROP TABLE IF EXISTS `t_act_model`;
CREATE TABLE IF NOT EXISTS `t_act_model` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `model_key` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_model 的数据：~0 rows (大约)
DELETE FROM `t_act_model`;
/*!40000 ALTER TABLE `t_act_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_model` ENABLE KEYS */;

-- 导出  表 village.t_act_node 结构
DROP TABLE IF EXISTS `t_act_node`;
CREATE TABLE IF NOT EXISTS `t_act_node` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `node_id` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `relate_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_node 的数据：~0 rows (大约)
DELETE FROM `t_act_node`;
/*!40000 ALTER TABLE `t_act_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_node` ENABLE KEYS */;

-- 导出  表 village.t_act_process 结构
DROP TABLE IF EXISTS `t_act_process`;
CREATE TABLE IF NOT EXISTS `t_act_process` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_id` bigint(20) unsigned DEFAULT NULL,
  `deployment_id` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `diagram_name` varchar(255) DEFAULT NULL,
  `latest` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `process_key` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `xml_name` varchar(255) DEFAULT NULL,
  `business_table` varchar(255) DEFAULT NULL,
  `route_name` varchar(255) DEFAULT NULL,
  `all_user` bit(1) DEFAULT NULL,
  `category_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_process 的数据：~0 rows (大约)
DELETE FROM `t_act_process`;
/*!40000 ALTER TABLE `t_act_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_process` ENABLE KEYS */;

-- 导出  表 village.t_act_starter 结构
DROP TABLE IF EXISTS `t_act_starter`;
CREATE TABLE IF NOT EXISTS `t_act_starter` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `process_def_id` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_act_starter 的数据：~0 rows (大约)
DELETE FROM `t_act_starter`;
/*!40000 ALTER TABLE `t_act_starter` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_act_starter` ENABLE KEYS */;

-- 导出  表 village.t_auto_chat 结构
DROP TABLE IF EXISTS `t_auto_chat`;
CREATE TABLE IF NOT EXISTS `t_auto_chat` (
  `id` bigint(20) unsigned NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `keywords` text,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `bad` int(11) DEFAULT NULL,
  `good` int(11) DEFAULT NULL,
  `content` text,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `evaluable` bit(1) DEFAULT NULL,
  `hot` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `search` (`title`,`keywords`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_auto_chat 的数据：~0 rows (大约)
DELETE FROM `t_auto_chat`;
/*!40000 ALTER TABLE `t_auto_chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_auto_chat` ENABLE KEYS */;

-- 导出  表 village.t_civilized_practice 结构
DROP TABLE IF EXISTS `t_civilized_practice`;
CREATE TABLE IF NOT EXISTS `t_civilized_practice` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_civilized_practice 的数据：~0 rows (大约)
DELETE FROM `t_civilized_practice`;
/*!40000 ALTER TABLE `t_civilized_practice` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_civilized_practice` ENABLE KEYS */;

-- 导出  表 village.t_client 结构
DROP TABLE IF EXISTS `t_client`;
CREATE TABLE IF NOT EXISTS `t_client` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `home_uri` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `redirect_uri` varchar(255) DEFAULT NULL,
  `auto_approve` bit(1) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_client 的数据：~0 rows (大约)
DELETE FROM `t_client`;
/*!40000 ALTER TABLE `t_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_client` ENABLE KEYS */;

-- 导出  表 village.t_convenient_service 结构
DROP TABLE IF EXISTS `t_convenient_service`;
CREATE TABLE IF NOT EXISTS `t_convenient_service` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_convenient_service 的数据：~0 rows (大约)
DELETE FROM `t_convenient_service`;
/*!40000 ALTER TABLE `t_convenient_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_convenient_service` ENABLE KEYS */;

-- 导出  表 village.t_department 结构
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE IF NOT EXISTS `t_department` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint(20) unsigned NOT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_department 的数据：~11 rows (大约)
DELETE FROM `t_department`;
/*!40000 ALTER TABLE `t_department` DISABLE KEYS */;
INSERT INTO `t_department` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `parent_id`, `sort_order`, `status`, `title`, `is_parent`) VALUES
	(40322777781112832, '', '2018-08-10 20:40:40', 0, '', '2018-08-11 00:03:06', 0, 1.00, 0, '总部', b'1'),
	(40322811096469504, '', '2018-08-10 20:40:48', 0, '', '2018-08-11 00:27:05', 40322777781112832, 1.00, 0, '技术部', b'1'),
	(40322852833988608, '', '2018-08-10 20:40:58', 0, '', '2018-08-11 01:29:42', 40322811096469504, 1.00, 0, '研发中心', NULL),
	(40327204755738624, '', '2018-08-10 20:58:15', 0, '', '2018-08-10 22:02:15', 40322811096469504, 2.00, 0, '大数据', NULL),
	(40327253309001728, '', '2018-08-10 20:58:27', 0, '', '2018-08-11 17:26:38', 40322811096469504, 3.00, -1, '人工智障', NULL),
	(40343262766043136, '', '2018-08-10 22:02:04', 0, '', '2018-08-11 00:02:53', 0, 2.00, 0, '成都分部', b'1'),
	(40344005342400512, '', '2018-08-10 22:05:01', 0, '', '2018-08-11 17:48:44', 40343262766043136, 2.00, 0, 'Vue', NULL),
	(40389030113710080, '', '2018-08-11 01:03:56', 0, '', '2018-08-11 17:50:04', 40343262766043136, 1.00, 0, 'JAVA', b'0'),
	(40652270295060480, '', '2018-08-11 18:29:57', 0, '', '2018-08-12 18:45:01', 0, 3.00, 0, '人事部', b'1'),
	(40652338142121984, NULL, '2018-08-11 18:30:13', 0, NULL, '2018-08-11 18:30:13', 40652270295060480, 1.00, 0, '游客', b'0'),
	(40681289119961088, '', '2018-08-11 20:25:16', 0, '', '2018-08-11 22:47:48', 40652270295060480, 2.00, 0, 'VIP', b'0');
/*!40000 ALTER TABLE `t_department` ENABLE KEYS */;

-- 导出  表 village.t_department_header 结构
DROP TABLE IF EXISTS `t_department_header`;
CREATE TABLE IF NOT EXISTS `t_department_header` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `department_id` bigint(20) unsigned DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_department_header 的数据：~0 rows (大约)
DELETE FROM `t_department_header`;
/*!40000 ALTER TABLE `t_department_header` DISABLE KEYS */;
INSERT INTO `t_department_header` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `department_id`, `type`, `user_id`) VALUES
	(1254427833757995008, 'admin', '2020-04-26 23:11:16', 0, 'admin', '2020-04-26 23:11:16', 40322777781112832, 0, 682265633886208);
/*!40000 ALTER TABLE `t_department_header` ENABLE KEYS */;

-- 导出  表 village.t_dict 结构
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE IF NOT EXISTS `t_dict` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_dict 的数据：~9 rows (大约)
DELETE FROM `t_dict`;
/*!40000 ALTER TABLE `t_dict` DISABLE KEYS */;
INSERT INTO `t_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `title`, `type`, `sort_order`) VALUES
	(75135930788220928, 'admin', '2018-11-14 22:15:43', 0, 'admin', '2018-11-27 01:39:06', '', '性别', 'sex', 0.00),
	(75383353938808832, 'admin', '2018-11-15 14:38:53', 0, 'admin', '2018-11-27 01:39:15', '', '消息类型', 'message_type', 1.00),
	(75388696739713024, 'admin', '2018-11-15 15:00:07', 0, 'admin', '2018-11-27 01:39:22', '', '按钮权限类型', 'permission_type', 2.00),
	(75392985935646720, 'admin', '2018-11-15 15:17:10', 0, 'admin', '2018-11-27 01:39:29', '', '腾讯云对象存储区域', 'tencent_bucket_region', 3.00),
	(79717732567748608, 'admin', '2018-11-27 13:42:10', 0, 'admin', '2018-11-27 13:42:10', '', '流程节点类型', 'process_node_type', 4.00),
	(81843858882695168, 'admin', '2018-12-03 10:30:38', 0, 'admin', '2018-12-03 10:30:49', '', '优先级', 'priority', 5.00),
	(82236987314016256, 'admin', '2018-12-04 12:32:47', 0, 'admin', '2018-12-04 12:32:47', '', '业务表', 'business_table', 6.00),
	(82236987314016257, 'admin', '2018-12-04 12:32:47', 0, 'admin', '2018-12-04 12:32:47', '', '业务表单路由', 'business_form', 7.00),
	(99020862912466944, 'admin', '2019-01-19 20:05:54', 0, 'admin', '2019-01-19 20:06:10', '', '请假类型', 'leave_type', 8.00);
/*!40000 ALTER TABLE `t_dict` ENABLE KEYS */;

-- 导出  表 village.t_dict_data 结构
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE IF NOT EXISTS `t_dict_data` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dict_id` bigint(20) unsigned DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort_order` (`sort_order`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_dict_data 的数据：~55 rows (大约)
DELETE FROM `t_dict_data`;
/*!40000 ALTER TABLE `t_dict_data` DISABLE KEYS */;
INSERT INTO `t_dict_data` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `dict_id`, `sort_order`, `status`, `title`, `value`) VALUES
	(75158227712479232, 'admin', '2018-11-14 23:44:19', 0, 'admin', '2019-04-01 23:53:13', '', 75135930788220928, 0.00, 0, '男', '男'),
	(75159254272577536, 'admin', '2018-11-14 23:48:24', 0, 'admin', '2019-04-01 23:53:17', '', 75135930788220928, 1.00, 0, '女', '女'),
	(75159898425397248, 'admin', '2018-11-14 23:50:57', 0, 'admin', '2019-04-01 23:53:22', '', 75135930788220928, 2.00, -1, '保密', '保密'),
	(75385648017575936, 'admin', '2018-11-15 14:48:00', 0, 'admin', '2019-04-02 00:10:36', '', 75383353938808832, 0.00, 0, '系统公告', '系统公告'),
	(75385706913992704, 'admin', '2018-11-15 14:48:14', 0, 'admin', '2019-04-02 00:10:32', '', 75383353938808832, 1.00, 0, '提醒', '提醒'),
	(75385774274514944, 'admin', '2018-11-15 14:48:30', 0, 'admin', '2019-04-02 00:10:28', '', 75383353938808832, 2.00, 0, '私信', '私信'),
	(75390787835138048, 'admin', '2018-11-15 15:08:26', 0, 'admin', '2018-11-15 15:08:26', '', 75388696739713024, 0.00, 0, '查看操作(view)', 'view'),
	(75390886501945344, 'admin', '2018-11-15 15:08:49', 0, 'admin', '2018-11-15 15:08:57', '', 75388696739713024, 1.00, 0, '添加操作(add)', 'add'),
	(75390993939042304, 'admin', '2018-11-15 15:09:15', 0, 'admin', '2018-11-15 15:09:15', '', 75388696739713024, 2.00, 0, '编辑操作(edit)', 'edit'),
	(75391067532300288, 'admin', '2018-11-15 15:09:32', 0, 'admin', '2018-11-15 15:09:32', '', 75388696739713024, 3.00, 0, '删除操作(delete)', 'delete'),
	(75391126902673408, 'admin', '2018-11-15 15:09:46', 0, 'admin', '2018-11-15 15:09:46', '', 75388696739713024, 4.00, 0, '清空操作(clear)', 'clear'),
	(75391192883269632, 'admin', '2018-11-15 15:10:02', 0, 'admin', '2018-11-15 15:10:02', '', 75388696739713024, 5.00, 0, '启用操作(enable)', 'enable'),
	(75391251024711680, 'admin', '2018-11-15 15:10:16', 0, 'admin', '2018-11-15 15:10:16', '', 75388696739713024, 6.00, 0, '禁用操作(disable)', 'disable'),
	(75391297124306944, 'admin', '2018-11-15 15:10:27', 0, 'admin', '2018-11-15 15:10:27', '', 75388696739713024, 7.00, 0, '搜索操作(search)', 'search'),
	(75391343379091456, 'admin', '2018-11-15 15:10:38', 0, 'admin', '2018-11-15 15:10:38', '', 75388696739713024, 8.00, 0, '上传文件(upload)', 'upload'),
	(75391407526776832, 'admin', '2018-11-15 15:10:53', 0, 'admin', '2018-11-15 15:10:53', '', 75388696739713024, 9.00, 0, '导出操作(output)', 'output'),
	(75391475042488320, 'admin', '2018-11-15 15:11:09', 0, 'admin', '2018-11-15 15:11:09', '', 75388696739713024, 10.00, 0, '导入操作(input)', 'input'),
	(75391522182270976, 'admin', '2018-11-15 15:11:21', 0, 'admin', '2018-11-15 15:11:21', '', 75388696739713024, 11.00, 0, '分配权限(editPerm)', 'editPerm'),
	(75391576364290048, 'admin', '2018-11-15 15:11:34', 0, 'admin', '2018-11-15 15:11:34', '', 75388696739713024, 12.00, 0, '设为默认(setDefault)', 'setDefault'),
	(75391798033256448, 'admin', '2018-11-15 15:12:26', 0, 'admin', '2018-11-15 15:12:26', '', 75388696739713024, 13.00, 0, '其他操作(other)', 'other'),
	(75393605291741184, 'admin', '2018-11-15 15:19:37', 0, 'admin', '2018-11-15 15:19:37', '', 75392985935646720, 0.00, 0, '北京一区（华北）', 'ap-beijing-1'),
	(75393681254780928, 'admin', '2018-11-15 15:19:55', 0, 'admin', '2018-11-15 15:19:55', '', 75392985935646720, 1.00, 0, '北京', 'ap-beijing'),
	(75393767619694592, 'admin', '2018-11-15 15:20:16', 0, 'admin', '2018-11-15 15:20:16', '', 75392985935646720, 2.00, 0, '上海（华东）', 'ap-shanghai'),
	(75393851484803072, 'admin', '2018-11-15 15:20:36', 0, 'admin', '2018-11-15 15:20:36', '', 75392985935646720, 3.00, 0, '广州（华南）', 'ap-guangzhou'),
	(75393961887272960, 'admin', '2018-11-15 15:21:02', 0, 'admin', '2018-11-15 15:21:02', '', 75392985935646720, 4.00, 0, '成都（西南）', 'ap-chengdu'),
	(75394053969022976, 'admin', '2018-11-15 15:21:24', 0, 'admin', '2018-11-15 15:21:24', '', 75392985935646720, 5.00, 0, '重庆', 'ap-chongqing'),
	(75394122474590208, 'admin', '2018-11-15 15:21:41', 0, 'admin', '2018-11-15 15:21:41', '', 75392985935646720, 6.00, 0, '新加坡', 'ap-singapore'),
	(75394186202845184, 'admin', '2018-11-15 15:21:56', 0, 'admin', '2018-11-15 15:21:56', '', 75392985935646720, 7.00, 0, '香港', 'ap-hongkong'),
	(75394254255427584, 'admin', '2018-11-15 15:22:12', 0, 'admin', '2018-11-15 15:22:12', '', 75392985935646720, 8.00, 0, '多伦多', 'na-toronto'),
	(75394309125312512, 'admin', '2018-11-15 15:22:25', 0, 'admin', '2018-11-15 15:22:25', '', 75392985935646720, 9.00, 0, '法兰克福', 'eu-frankfurt'),
	(75394367044456448, 'admin', '2018-11-15 15:22:39', 0, 'admin', '2018-11-15 15:22:39', '', 75392985935646720, 10.00, 0, '孟买', 'ap-mumbai'),
	(75394448523005952, 'admin', '2018-11-15 15:22:58', 0, 'admin', '2018-11-15 15:22:58', '', 75392985935646720, 11.00, 0, '首尔', 'ap-seoul'),
	(75394604765024256, 'admin', '2018-11-15 15:23:36', 0, 'admin', '2018-11-15 15:23:36', '', 75392985935646720, 12.00, 0, '硅谷', 'na-siliconvalley'),
	(75394659299364864, 'admin', '2018-11-15 15:23:49', 0, 'admin', '2018-11-15 15:23:49', '', 75392985935646720, 13.00, 0, '弗吉尼亚', 'na-ashburn'),
	(75394705700950016, 'admin', '2018-11-15 15:24:00', 0, 'admin', '2018-11-15 15:24:00', '', 75392985935646720, 14.00, 0, '曼谷', 'ap-bangkok'),
	(75394759287377920, 'admin', '2018-11-15 15:24:12', 0, 'admin', '2018-11-15 15:24:12', '', 75392985935646720, 15.00, 0, '莫斯科', 'eu-moscow'),
	(79717808262352896, 'admin', '2018-11-27 13:42:28', 0, 'admin', '2018-11-27 13:42:28', '', 79717732567748608, 0.00, 0, '开始节点', '0'),
	(79717858308788224, 'admin', '2018-11-27 13:42:40', 0, 'admin', '2018-11-27 13:42:40', '', 79717732567748608, 1.00, 0, '审批节点', '1'),
	(79717920061526016, 'admin', '2018-11-27 13:42:54', 0, 'admin', '2018-12-08 20:35:39', '', 79717732567748608, 2.00, 0, '结束节点', '2'),
	(81843987719131136, 'admin', '2018-12-03 10:31:08', 0, 'admin', '2018-12-03 10:31:08', '', 81843858882695168, 0.00, 0, '普通', '0'),
	(81844044015079424, 'admin', '2018-12-03 10:31:22', 0, 'admin', '2018-12-03 10:31:22', '', 81843858882695168, 1.00, 0, '重要', '1'),
	(81844100705292288, 'admin', '2018-12-03 10:31:35', 0, 'admin', '2018-12-03 10:31:35', '', 81843858882695168, 2.00, 0, '紧急', '2'),
	(82237106587439104, 'admin', '2018-12-04 12:33:15', 0, 'admin', '2018-12-04 12:33:15', '', 82236987314016256, 0.00, 0, '请假申请表', 't_leave'),
	(82237106587439105, 'admin', '2018-12-04 12:33:15', 0, 'admin', '2018-12-04 12:33:15', '', 82236987314016257, 0.00, 0, '请假申请表', 'leave'),
	(99020985985929216, 'admin', '2019-01-19 20:06:23', 0, 'admin', '2019-04-01 23:55:48', '', 99020862912466944, 0.00, 0, '年假', '年假'),
	(99021020739932160, 'admin', '2019-01-19 20:06:32', 0, 'admin', '2019-04-01 23:55:52', '', 99020862912466944, 1.00, 0, '事假', '事假'),
	(99021195566911488, 'admin', '2019-01-19 20:07:13', 0, 'admin', '2019-04-01 23:55:56', '', 99020862912466944, 2.00, 0, '病假', '病假'),
	(99021242476007424, 'admin', '2019-01-19 20:07:24', 0, 'admin', '2019-04-01 23:56:01', '', 99020862912466944, 3.00, 0, '调休', '调休'),
	(99021300730695680, 'admin', '2019-01-19 20:07:38', 0, 'admin', '2019-04-01 23:56:05', '', 99020862912466944, 4.00, 0, '产假', '产假'),
	(99021341889400832, 'admin', '2019-01-19 20:07:48', 0, 'admin', '2019-04-01 23:56:10', '', 99020862912466944, 5.00, 0, '陪产假', '陪产假'),
	(99021382393794560, 'admin', '2019-01-19 20:07:58', 0, 'admin', '2019-04-01 23:56:14', '', 99020862912466944, 6.00, 0, '婚假', '婚假'),
	(99021437171404800, 'admin', '2019-01-19 20:08:11', 0, 'admin', '2019-04-01 23:56:18', '', 99020862912466944, 7.00, 0, '例假', '例假'),
	(99021497724571648, 'admin', '2019-01-19 20:08:25', 0, 'admin', '2019-04-01 23:56:23', '', 99020862912466944, 8.00, 0, '丧假', '丧假'),
	(99021556704874496, 'admin', '2019-01-19 20:08:39', 0, 'admin', '2019-04-01 23:56:27', '', 99020862912466944, 9.00, 0, '哺乳假', '哺乳假'),
	(125170157323554816, 'admin', '2019-04-01 23:53:52', 0, 'admin', '2019-04-01 23:53:52', '', 75383353938808832, 3.00, 0, '工作流', '工作流');
/*!40000 ALTER TABLE `t_dict_data` ENABLE KEYS */;

-- 导出  表 village.t_dynamic_build 结构
DROP TABLE IF EXISTS `t_dynamic_build`;
CREATE TABLE IF NOT EXISTS `t_dynamic_build` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_dynamic_build 的数据：~0 rows (大约)
DELETE FROM `t_dynamic_build`;
/*!40000 ALTER TABLE `t_dynamic_build` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dynamic_build` ENABLE KEYS */;

-- 导出  表 village.t_family_style 结构
DROP TABLE IF EXISTS `t_family_style`;
CREATE TABLE IF NOT EXISTS `t_family_style` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_family_style 的数据：~0 rows (大约)
DELETE FROM `t_family_style`;
/*!40000 ALTER TABLE `t_family_style` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_family_style` ENABLE KEYS */;

-- 导出  表 village.t_file 结构
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE IF NOT EXISTS `t_file` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint(20) unsigned DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `f_key` varchar(255) DEFAULT NULL,
  `location` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_file 的数据：~0 rows (大约)
DELETE FROM `t_file`;
/*!40000 ALTER TABLE `t_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_file` ENABLE KEYS */;

-- 导出  表 village.t_folk_activities 结构
DROP TABLE IF EXISTS `t_folk_activities`;
CREATE TABLE IF NOT EXISTS `t_folk_activities` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_folk_activities 的数据：~0 rows (大约)
DELETE FROM `t_folk_activities`;
/*!40000 ALTER TABLE `t_folk_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_folk_activities` ENABLE KEYS */;

-- 导出  表 village.t_governance_effectiveness 结构
DROP TABLE IF EXISTS `t_governance_effectiveness`;
CREATE TABLE IF NOT EXISTS `t_governance_effectiveness` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_governance_effectiveness 的数据：~0 rows (大约)
DELETE FROM `t_governance_effectiveness`;
/*!40000 ALTER TABLE `t_governance_effectiveness` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_governance_effectiveness` ENABLE KEYS */;

-- 导出  表 village.t_learning_materials 结构
DROP TABLE IF EXISTS `t_learning_materials`;
CREATE TABLE IF NOT EXISTS `t_learning_materials` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_learning_materials 的数据：~0 rows (大约)
DELETE FROM `t_learning_materials`;
/*!40000 ALTER TABLE `t_learning_materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_learning_materials` ENABLE KEYS */;

-- 导出  表 village.t_leave 结构
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE IF NOT EXISTS `t_leave` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `duration` int(11) unsigned DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_leave 的数据：~0 rows (大约)
DELETE FROM `t_leave`;
/*!40000 ALTER TABLE `t_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_leave` ENABLE KEYS */;

-- 导出  表 village.t_legal_counsel 结构
DROP TABLE IF EXISTS `t_legal_counsel`;
CREATE TABLE IF NOT EXISTS `t_legal_counsel` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_legal_counsel 的数据：~0 rows (大约)
DELETE FROM `t_legal_counsel`;
/*!40000 ALTER TABLE `t_legal_counsel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_legal_counsel` ENABLE KEYS */;

-- 导出  表 village.t_log 结构
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE IF NOT EXISTS `t_log` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cost_time` int(11) unsigned DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` longtext,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `log_type` tinyint(1) DEFAULT NULL,
  `device` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_log 的数据：~14 rows (大约)
DELETE FROM `t_log`;
/*!40000 ALTER TABLE `t_log` DISABLE KEYS */;
INSERT INTO `t_log` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `cost_time`, `ip`, `ip_info`, `name`, `request_param`, `request_type`, `request_url`, `username`, `log_type`, `device`) VALUES
	(1406517591316500480, NULL, '2021-06-20 15:41:40', 0, NULL, '2021-06-20 15:41:40', 206, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"KG1Y","saveLogin":"true","captchaId":"47008b9107be4aceb2ad11091aaa36f7","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406517642902245376, NULL, '2021-06-20 15:41:53', 0, NULL, '2021-06-20 15:41:53', 46, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"YEEA","saveLogin":"true","captchaId":"c518ac38cdfa480d98d29c3f3269033d","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406517818471616512, NULL, '2021-06-20 15:42:34', 0, NULL, '2021-06-20 15:42:34', 45, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"ncvs","saveLogin":"true","captchaId":"6565b8afa3c94a939b2690c9bbe2ff25","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406518158306709504, NULL, '2021-06-20 15:43:56', 0, NULL, '2021-06-20 15:43:56', 110, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"PDT7","saveLogin":"true","captchaId":"1b03bc46618d4d26b94f8098b17a3224","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406518483310743552, NULL, '2021-06-20 15:45:13', 0, NULL, '2021-06-20 15:45:13', 69, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"YRQH","saveLogin":"true","captchaId":"b437f143ad8f44ff8d7427ca5771a81b","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406518708138020864, NULL, '2021-06-20 15:46:07', 0, NULL, '2021-06-20 15:46:07', 60, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"OSYM","saveLogin":"true","captchaId":"6079ba596c57433399a2b3b1a4f8c51f","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406518846768156672, NULL, '2021-06-20 15:46:40', 0, NULL, '2021-06-20 15:46:40', 88, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"KHZH","saveLogin":"true","captchaId":"2fd0121e4a5f4edcbf458d714f75bb38","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406519159159918592, NULL, '2021-06-20 15:47:54', 0, NULL, '2021-06-20 15:47:54', 51, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"URXS","saveLogin":"true","captchaId":"8f053e34c6ae4e19afab2938d075c17e","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406520016609873920, NULL, '2021-06-20 15:51:19', 0, NULL, '2021-06-20 15:51:19', 63, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"7CVZ","saveLogin":"true","captchaId":"0ea07732415048c193fbb452260b3be1","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406520651539419136, NULL, '2021-06-20 15:53:50', 0, NULL, '2021-06-20 15:53:50', 56, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"OXOW","saveLogin":"true","captchaId":"2a7b2f14ef4a444eaf7011da21c61d68","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406522028940464128, NULL, '2021-06-20 15:59:18', 0, NULL, '2021-06-20 15:59:18', 55, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"HHPA","saveLogin":"true","captchaId":"6e58b7ce9a9c442b99ca455812eadc58","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406857597465792512, NULL, '2021-06-21 14:12:44', 0, NULL, '2021-06-21 14:12:44', 217, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"ws5z","saveLogin":"true","captchaId":"1ae4a3f68dc44265a3db6d4461e4bba3","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406857666600505344, NULL, '2021-06-21 14:13:01', 0, NULL, '2021-06-21 14:13:01', 76, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"p2wd","saveLogin":"true","captchaId":"974d00559a6a41c0b22ebb5640c41cfc","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1406868658533306368, NULL, '2021-06-21 14:56:41', 0, NULL, '2021-06-21 14:56:41', 99, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"EK2Z","saveLogin":"true","captchaId":"b6da85ab37184793a245f8a42ce1e3f3","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 91.0.4472.101 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1420343572816859136, NULL, '2021-07-28 19:21:11', 0, NULL, '2021-07-28 19:21:11', 273, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"KHG8","saveLogin":"true","captchaId":"30a261d22e854bee803744e043d84cd1","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.107 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1420343632656994304, NULL, '2021-07-28 19:21:25', 0, NULL, '2021-07-28 19:21:25', 63, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"EFXK","saveLogin":"true","captchaId":"582d17ad7a8e418ba26c0d66775d1c73","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.107 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1422164670197927936, NULL, '2021-08-02 19:57:35', 0, NULL, '2021-08-02 19:57:35', 254, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"7MTU","saveLogin":"true","captchaId":"5ddbc9cc78a847509ba82ff43c050e82","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.107 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1422355322437242880, NULL, '2021-08-03 08:35:10', 0, NULL, '2021-08-03 08:35:10', 274, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"qbqh","saveLogin":"true","captchaId":"242ff36593c5498da2d5063f62894987","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.107 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1423905026643988480, NULL, '2021-08-07 15:13:08', 0, NULL, '2021-08-07 15:13:08', 199, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"6ZNX","saveLogin":"true","captchaId":"b9c62a3fbd4b4160a58da5ce399e95c5","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1423905088958763008, NULL, '2021-08-07 15:13:23', 0, NULL, '2021-08-07 15:13:23', 57, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"2CVV","saveLogin":"true","captchaId":"76635122d52b4437bd304e2356275a1c","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1423920136909361152, NULL, '2021-08-07 16:13:11', 0, NULL, '2021-08-07 16:13:11', 87, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"JAMP","saveLogin":"true","captchaId":"73132c4f92f9471bbccf4a6a68078a75","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1424171478974337024, NULL, '2021-08-08 08:51:55', 0, NULL, '2021-08-08 08:51:55', 283, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"ypdx","saveLogin":"true","captchaId":"d737a0cb30124f9dbbd9f67a3ea94a92","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1424185304335650816, NULL, '2021-08-08 09:46:52', 0, NULL, '2021-08-08 09:46:52', 223, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"sdyh","saveLogin":"true","captchaId":"34bbf25a177a4fc4a114990598dce203","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1424186291012112384, NULL, '2021-08-08 09:50:47', 0, NULL, '2021-08-08 09:50:47', 90, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"q1u3","saveLogin":"true","captchaId":"6b8823ae9a114881aded76b76731692b","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端'),
	(1424255214025838592, NULL, '2021-08-08 14:24:39', 0, NULL, '2021-08-08 14:24:39', 222, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"VQ1M","saveLogin":"true","captchaId":"155702ff101a45b296bb888a6e47fedb","username":"admin"}', 'POST', '/xboot/login', 'admin', 1, 'Chrome 92.0.4515.131 | Windows Windows 10 or Windows Server 2016 | PC端');
/*!40000 ALTER TABLE `t_log` ENABLE KEYS */;

-- 导出  表 village.t_meeting 结构
DROP TABLE IF EXISTS `t_meeting`;
CREATE TABLE IF NOT EXISTS `t_meeting` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_meeting 的数据：~0 rows (大约)
DELETE FROM `t_meeting`;
/*!40000 ALTER TABLE `t_meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_meeting` ENABLE KEYS */;

-- 导出  表 village.t_message 结构
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE IF NOT EXISTS `t_message` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_send` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4,
  `is_template` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_message 的数据：~5 rows (大约)
DELETE FROM `t_message`;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
INSERT INTO `t_message` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `create_send`, `title`, `type`, `content`, `is_template`) VALUES
	(43615268366192640, 'admin', '2018-08-19 22:43:51', 0, 'admin', '2021-01-01 14:49:39', b'1', '欢迎您注册账号使用XBoot 点我查看使用须知', '系统公告', '<p style="text-align: center;">XBoot是很棒的Web前后端分离开发平台，开源版本请遵循GPL v3.0开源协议，不得闭源，商用需求请联系作者签署授权协议。</p><p style="text-align: center;">捐赠获取完整版：<a href="http://xpay.exrick.cn/pay?xboot" target="_blank" style="background-color: rgb(255, 255, 255);">立即去捐赠获取</a></p><p style="text-align: center;"><img src="https://i.loli.net/2018/08/24/5b801c8652227.png" style="max-width:150px;"><br></p><p style="text-align: center;">手机扫一扫支付</p><p style="text-align: center;">获取商用授权：<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=1012139570&amp;site=qq&amp;menu=yes" target="_blank" style="background-color: rgb(255, 255, 255);">立即联系作者获取商用授权</a></p>', b'0'),
	(1352138048674074624, 'admin', '2021-01-21 14:16:47', 0, 'admin', '2021-01-22 01:35:38', b'0', '您收到了一个新的委托转办的“${processName}”任务，申请人：${applyer}', '工作流', '<p>${nickname}您好，<span style="font-size: 1em;">您收到了一个新的</span>委托转办的“${processName}”任务，申请人：${nickname}，赶快去系统“工作流程-我的待办”中查看处理吧！</p>', b'1'),
	(1352243408286126080, 'admin', '2021-01-21 21:15:27', 0, 'admin', '2021-01-22 01:35:21', b'0', '您收到了一条新的“${processName}”待办任务，申请人：${applyer}', '工作流', '<p>${nickname}您好，您收到了一条新的“${processName}”待办任务，申请人：${applyer}，赶快去“工作流程-我的待办”处理查看吧<br></p>', b'1'),
	(1352244242726129664, 'admin', '2021-01-21 21:18:46', 0, 'admin', '2021-01-22 01:34:57', b'0', '您申请的“${processName}”任务已审批通过', '工作流', '<p>${nickname}您好，您申请的“${processName}”任务已审批通过，赶快去“工作流程-我的申请”处理查看吧<br></p>', b'1'),
	(1352304101387538432, 'admin', '2021-01-22 01:16:37', 0, 'admin', '2021-01-22 01:33:48', b'0', '${nickname}您好，您申请的“${processName}”任务已被驳回', '工作流', '<p>${nickname}您好，您申请的“${processName}”任务已被驳回，赶快去“工作流程-我的申请”处理查看吧<br></p>', b'1');
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;

-- 导出  表 village.t_message_send 结构
DROP TABLE IF EXISTS `t_message_send`;
CREATE TABLE IF NOT EXISTS `t_message_send` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `message_id` bigint(20) unsigned DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_message_send 的数据：~0 rows (大约)
DELETE FROM `t_message_send`;
/*!40000 ALTER TABLE `t_message_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_message_send` ENABLE KEYS */;

-- 导出  表 village.t_online_examination 结构
DROP TABLE IF EXISTS `t_online_examination`;
CREATE TABLE IF NOT EXISTS `t_online_examination` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_online_examination 的数据：~0 rows (大约)
DELETE FROM `t_online_examination`;
/*!40000 ALTER TABLE `t_online_examination` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_online_examination` ENABLE KEYS */;

-- 导出  表 village.t_permission 结构
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE IF NOT EXISTS `t_permission` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) unsigned NOT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int(11) unsigned DEFAULT NULL,
  `button_type` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `show_always` bit(1) DEFAULT NULL,
  `is_menu` bit(1) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_permission 的数据：~44 rows (大约)
DELETE FROM `t_permission`;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `name`, `parent_id`, `type`, `sort_order`, `component`, `path`, `title`, `icon`, `level`, `button_type`, `status`, `url`, `show_always`, `is_menu`, `is_parent`) VALUES
	(5129710648430592, '', '2018-06-04 19:02:29', 0, 'admin', '2021-01-11 01:00:01', '', 'sys', 125909152017944576, 0, 1.00, 'Main', '/sys', '系统管理', 'ios-settings', 1, '', 0, '', b'1', NULL, b'1'),
	(5129710648430593, '', '2018-06-04 19:02:32', 0, '', '2018-08-13 15:15:33', '', 'user-manage', 5129710648430592, 0, 1.10, 'sys/user-manage/userManage', 'user-manage', '用户管理', 'md-person', 2, '', 0, '', b'1', NULL, b'1'),
	(5129710648430594, '', '2018-06-04 19:02:35', 0, '', '2018-10-13 13:51:36', '', 'role-manage', 5129710648430592, 0, 1.60, 'sys/role-manage/roleManage', 'role-manage', '角色权限管理', 'md-contacts', 2, '', 0, '', b'1', NULL, b'1'),
	(5129710648430595, '', '2018-06-04 19:02:37', 0, '', '2018-09-23 23:32:02', '', 'menu-manage', 5129710648430592, 0, 1.70, 'sys/menu-manage/menuManage', 'menu-manage', '菜单权限管理', 'md-menu', 2, '', 0, '', b'1', NULL, b'1'),
	(15701400130424832, '', '2018-06-03 22:04:06', 0, '', '2018-09-19 22:16:44', '', '', 5129710648430593, 1, 1.11, '', '/xboot/user/admin/add*', '添加用户', '', 3, 'add', 0, '', b'1', NULL, NULL),
	(15701915807518720, '', '2018-06-03 22:06:09', 0, '', '2018-06-06 14:46:51', '', '', 5129710648430593, 1, 1.13, '', '/xboot/user/admin/disable/**', '禁用用户', '', 3, 'disable', 0, NULL, b'1', NULL, NULL),
	(15708892205944832, '', '2018-06-03 22:33:52', 0, '', '2018-06-28 16:44:48', '', '', 5129710648430593, 1, 1.14, '', '/xboot/user/admin/enable/**', '启用用户', '', 3, 'enable', 0, NULL, b'1', NULL, NULL),
	(16678126574637056, '', '2018-06-06 14:45:16', 0, '', '2018-09-19 22:16:48', '', '', 5129710648430593, 1, 1.12, '', '/xboot/user/admin/edit*', '编辑用户', '', 3, 'edit', 0, '', b'1', NULL, NULL),
	(16678447719911424, '', '2018-06-06 14:46:32', 0, '', '2018-08-10 21:41:16', '', '', 5129710648430593, 1, 1.15, '', '/xboot/user/delByIds/**', '删除用户', '', 3, 'delete', 0, '', b'1', NULL, NULL),
	(16687383932047360, '', '2018-06-06 15:22:03', 0, '', '2018-09-19 22:07:34', '', '', 5129710648430594, 1, 1.21, '', '/xboot/role/save*', '添加角色', '', 3, 'add', 0, '', b'1', NULL, NULL),
	(16689632049631232, '', '2018-06-06 15:30:59', 0, '', '2018-09-19 22:07:37', '', '', 5129710648430594, 1, 1.22, '', '/xboot/role/edit*', '编辑角色', '', 3, 'edit', 0, '', b'1', NULL, NULL),
	(16689745006432256, '', '2018-06-06 15:31:26', 0, '', '2018-08-10 21:41:23', '', '', 5129710648430594, 1, 1.23, '', '/xboot/role/delAllByIds/**', '删除角色', '', 3, 'delete', 0, '', b'1', NULL, NULL),
	(16689883993083904, NULL, '2018-06-06 15:31:59', 0, NULL, '2018-06-06 15:31:59', NULL, NULL, 5129710648430594, 1, 1.24, NULL, '/xboot/role/editRolePerm**', '分配权限', NULL, 3, 'editPerm', 0, NULL, b'1', NULL, NULL),
	(16690313745666048, '', '2018-06-06 15:33:41', 0, '', '2018-09-19 22:07:46', '', '', 5129710648430594, 1, 1.25, '', '/xboot/role/setDefault*', '设为默认角色', '', 3, 'setDefault', 0, '', b'1', NULL, NULL),
	(16694861252005888, '', '2018-06-06 15:51:46', 0, '', '2018-09-19 22:07:52', '', '', 5129710648430595, 1, 1.31, '', '/xboot/permission/add*', '添加菜单', '', 3, 'add', 0, '', b'1', NULL, NULL),
	(16695107491205120, '', '2018-06-06 15:52:44', 0, '', '2018-09-19 22:07:57', '', '', 5129710648430595, 1, 1.32, '', '/xboot/permission/edit*', '编辑菜单', '', 3, 'edit', 0, '', b'1', NULL, NULL),
	(16695243126607872, '', '2018-06-06 15:53:17', 0, '', '2018-08-10 21:41:33', '', '', 5129710648430595, 1, 1.33, '', '/xboot/permission/delByIds/**', '删除菜单', '', 3, 'delete', 0, '', b'1', NULL, NULL),
	(25014528525733888, '', '2018-06-29 14:51:09', 0, '', '2018-10-08 11:13:27', '', '', 5129710648430593, 1, 1.16, '', '无', '上传图片', '', 3, 'upload', 0, '', b'1', NULL, NULL),
	(39915540965232640, NULL, '2018-08-09 17:42:28', 0, NULL, '2018-08-09 17:42:28', NULL, 'monitor', 125909152017944576, 0, 2.00, 'Main', '/monitor', '系统监控', 'ios-analytics', 1, NULL, 0, NULL, b'1', NULL, b'1'),
	(40238597734928384, '', '2018-08-10 15:06:10', 0, 'admin', '2020-12-10 02:21:33', '', 'department-manage', 5129710648430592, 0, 1.20, 'sys/department-manage/departmentManage', 'department-manage', '部门管理', 'md-git-branch', 2, '', 0, '', b'1', NULL, b'1'),
	(41363147411427328, '', '2018-08-13 17:34:43', 0, 'admin', '2020-03-25 20:31:16', '', 'log-manage', 39915540965232640, 0, 2.20, 'sys/log-manage/logManage', 'log-manage', '日志管理', 'md-list-box', 2, '', 0, '', b'1', NULL, b'1'),
	(41363537456533504, '', '2018-08-13 17:36:16', 0, '', '2018-08-13 17:56:11', '', '', 41363147411427328, 1, 2.11, '', '/xboot/log/delByIds/**', '删除日志', '', 3, 'delete', 0, '', b'1', NULL, NULL),
	(41364927394353152, '', '2018-08-13 17:41:48', 0, 'admin', '2020-12-11 16:32:40', '', '', 41363147411427328, 1, 2.12, '', '/xboot/log/delAll*', '清空日志', '', 3, 'clear', 0, '', b'1', NULL, NULL),
	(45235621697949696, '', '2018-08-24 10:02:33', 0, '', '2018-09-19 22:06:57', '', '', 40238597734928384, 1, 1.21, '', '/xboot/department/add*', '添加部门', '', 3, 'add', 0, '', b'1', NULL, NULL),
	(45235787867885568, '', '2018-08-24 10:03:13', 0, '', '2018-09-19 22:07:02', '', '', 40238597734928384, 1, 1.22, '', '/xboot/department/edit*', '编辑部门', '', 3, 'edit', 0, '', b'1', NULL, NULL),
	(45235939278065664, NULL, '2018-08-24 10:03:49', 0, NULL, '2018-08-24 10:03:49', NULL, '', 40238597734928384, 1, 1.23, '', '/xboot/department/delByIds/*', '删除部门', '', 3, 'delete', 0, NULL, b'1', NULL, NULL),
	(56309618086776832, '', '2018-09-23 23:26:40', 0, 'admin', '2021-06-21 14:29:10', '', 'oss-manage', 5129710648430592, 0, 1.40, 'sys/oss-manage/ossManage', 'oss-manage', '云盘', 'ios-folder', 2, '', 0, '', b'1', NULL, b'1'),
	(56898976661639168, '', '2018-09-25 14:28:34', 0, '', '2018-09-25 15:12:46', '', '', 5129710648430593, 1, 1.17, '', '/xboot/user/importData*', '导入用户', '', 3, 'input', 0, '', b'1', NULL, NULL),
	(57212882168844288, '', '2018-09-26 11:15:55', 0, '', '2018-10-08 11:10:05', '', '', 56309618086776832, 1, 1.41, '', '无', '上传文件', '', 3, 'upload', 0, '', b'1', NULL, NULL),
	(58480609315524608, '', '2018-09-29 23:13:24', 0, 'admin', '2021-06-21 14:19:55', '', 'setting', 5129710648430592, 0, 1.90, 'sys/setting-manage/settingManage', 'setting', '系统配置', 'ios-settings-outline', 2, '', 0, '', b'1', NULL, b'1'),
	(61394706252173312, NULL, '2018-10-08 00:12:59', 0, NULL, '2018-10-08 00:12:59', NULL, '', 58480609315524608, 1, 1.81, '', '/xboot/setting/seeSecret/**', '查看私密配置', '', 3, 'view', 0, NULL, b'1', NULL, NULL),
	(61417744146370560, '', '2018-10-08 01:44:32', 0, '', '2018-10-08 01:50:03', '', '', 58480609315524608, 1, 1.82, '', '/xboot/setting/*/set*', '编辑配置', '', 3, 'edit', 0, '', b'1', NULL, NULL),
	(61560041605435392, NULL, '2018-10-08 11:09:58', 0, NULL, '2018-10-08 11:09:58', NULL, '', 56309618086776832, 1, 1.42, '', '/xboot/file/rename*', '重命名文件', '', 3, 'edit', 0, NULL, b'1', NULL, NULL),
	(61560275261722624, NULL, '2018-10-08 11:10:54', 0, NULL, '2018-10-08 11:10:54', NULL, '', 56309618086776832, 1, 1.43, '', '/xboot/file/copy*', '复制文件', '', 3, 'edit', 0, NULL, b'1', NULL, NULL),
	(61560480518377472, NULL, '2018-10-08 11:11:43', 0, NULL, '2018-10-08 11:11:43', NULL, '', 56309618086776832, 1, 1.44, '', '/xboot/file/delete/*', '删除文件', '', 3, 'delete', 0, NULL, b'1', NULL, NULL),
	(75002207560273920, 'admin', '2018-11-14 13:24:21', 0, 'admin', '2018-11-20 20:06:22', '', 'dict', 5129710648430592, 0, 1.80, 'sys/dict-manage/dictManage', 'dict', '数据字典管理', 'md-bookmarks', 2, '', 0, '', b'1', NULL, b'1'),
	(76215889006956544, 'admin', '2018-11-17 21:47:05', 0, 'admin', '2018-11-17 21:47:53', '', '', 75002207560273920, 1, 1.81, '', '/xboot/dict/add*', '添加字典', '', 3, 'add', 0, '', b'1', NULL, NULL),
	(76216071333351424, 'admin', '2018-11-17 21:47:48', 0, 'admin', '2018-11-17 21:47:48', NULL, '', 75002207560273920, 1, 1.82, '', '/xboot/dict/edit*', '编辑字典', '', 3, 'edit', 0, NULL, b'1', NULL, NULL),
	(76216264070008832, 'admin', '2018-11-17 21:48:34', 0, 'admin', '2018-11-17 21:48:34', NULL, '', 75002207560273920, 1, 1.83, '', '/xboot/dict/delByIds/**', '删除字典', '', 3, 'delete', 0, NULL, b'1', NULL, NULL),
	(76216459709124608, 'admin', '2018-11-17 21:49:21', 0, 'admin', '2018-11-17 21:49:21', NULL, '', 75002207560273920, 1, 1.84, '', '/xboot/dictData/add*', '添加字典数据', '', 3, 'add', 0, NULL, b'1', NULL, NULL),
	(76216594207870976, 'admin', '2018-11-17 21:49:53', 0, 'admin', '2018-11-17 21:49:53', NULL, '', 75002207560273920, 1, 1.85, '', '/xboot/dictData/edit*', '编辑字典数据', '', 3, 'edit', 0, NULL, b'1', NULL, NULL),
	(76216702639017984, 'admin', '2018-11-17 21:50:18', 0, 'admin', '2018-11-17 21:50:18', NULL, '', 75002207560273920, 1, 1.86, '', '/xboot/dictData/delByIds/**', '删除字典数据', '', 3, 'delete', 0, NULL, b'1', NULL, NULL),
	(125909152017944576, 'admin', '2019-04-04 00:50:22', 0, 'admin', '2021-06-21 14:19:14', 'undefined', 'xboot', 0, -1, 0.00, 'false', '', '系统基础', 'md-home', 0, '', 0, '', b'1', b'1', b'1'),
	(156365156580855808, 'admin', '2019-06-27 01:51:39', 0, 'admin', '2019-06-27 01:51:39', NULL, '', 5129710648430593, 1, 1.18, '', '/xboot/user/resetPass', '重置密码', '', 3, 'other', 0, NULL, b'1', NULL, NULL),
	(1422168282022154240, 'admin', '2021-08-02 20:11:56', 0, 'admin', '2021-08-02 20:11:56', NULL, 'vueTemp', 5129710648430592, 0, 8.00, 'sys/oneJavaVue/tableGenerator', 'vueTemp', 'Vue', 'md-analytics', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422355389407694849, 'admin', '2021-08-03 08:35:26', 0, 'admin', '2021-08-03 08:37:21', NULL, 'zhdj', 0, -1, 2.00, NULL, NULL, '智慧党建', 'md-albums', 0, NULL, 0, NULL, b'1', b'1', b'1'),
	(1422355444546015232, 'admin', '2021-08-03 08:35:39', 0, 'admin', '2021-08-03 08:44:30', NULL, 'xczz', 0, -1, 3.00, NULL, NULL, '乡村自治', 'ios-apps', 0, NULL, 0, NULL, b'1', b'1', b'1'),
	(1422355497952088065, 'admin', '2021-08-03 08:35:51', 0, 'admin', '2021-08-03 08:46:10', NULL, 'xcfz', 0, -1, 4.00, NULL, NULL, '乡村法治', 'md-analytics', 0, NULL, 0, NULL, b'1', b'1', b'1'),
	(1422355557112745984, 'admin', '2021-08-03 08:36:06', 0, 'admin', '2021-08-03 08:46:53', NULL, 'xcdz', 0, -1, 5.00, NULL, NULL, '乡村德治', 'md-alarm', 0, NULL, 0, NULL, b'1', b'1', b'1'),
	(1422355621264625664, 'admin', '2021-08-03 08:36:21', 0, 'admin', '2021-08-03 08:48:02', NULL, 'bmfw', 0, -1, 6.00, NULL, NULL, '便民服务', 'md-at', 0, NULL, 0, NULL, b'1', b'1', b'1'),
	(1422355875284258816, 'admin', '2021-08-03 08:37:21', 0, 'admin', '2021-08-03 08:50:04', NULL, 'dynamicBuild', 1422355389407694849, 0, 1.00, 'Main', '/dynamicBuild', '党建动态', 'ios-alarm', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422356085288865793, 'admin', '2021-08-03 08:38:12', 0, 'admin', '2021-08-03 08:50:31', NULL, 'meeting', 1422355389407694849, 0, 2.00, 'Main', '/meeting', '党员管理', 'ios-apps', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422356835742126080, 'admin', '2021-08-03 08:41:10', 0, 'admin', '2021-08-03 08:50:52', NULL, 'onlineExamination', 1422355389407694849, 0, 3.00, 'Main', '/onlineExamination', '在线考试', 'ios-appstore', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422357545913290753, 'admin', '2021-08-03 08:44:00', 0, 'admin', '2021-08-03 08:51:10', NULL, 'learningMaterials', 1422355389407694849, 0, 4.00, 'Main', '/learningMaterials', '在线学习', 'ios-appstore', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422357672317030401, 'admin', '2021-08-03 08:44:30', 0, 'admin', '2021-08-03 08:51:32', NULL, 'villageIntroduce', 1422355444546015232, 0, 1.00, 'Main', '/villageIntroduce', '村情介绍', 'md-apps', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422357764453306369, 'admin', '2021-08-03 08:44:52', 0, 'admin', '2021-08-03 08:51:49', '', 'taskDisclosure', 1422355444546015232, 0, 2.00, 'Main', '/taskDisclosure', '村务公开', 'md-archive', 1, '', 0, '', b'1', b'1', b'1'),
	(1422357883705757697, 'admin', '2021-08-03 08:45:20', 0, 'admin', '2021-08-03 08:52:05', NULL, 'governanceEffectiveness', 1422355444546015232, 0, 3.00, 'Main', '/governanceEffectiveness', '治理成效', 'ios-basket', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422357979881148416, 'admin', '2021-08-03 08:45:43', 0, 'admin', '2021-08-03 08:52:23', NULL, 'villageAppointment', 1422355444546015232, 0, 4.00, 'Main', '/villageAppointment', '村规民约', 'ios-bonfire', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358091441246208, 'admin', '2021-08-03 08:46:10', 0, 'admin', '2021-08-03 08:52:43', NULL, 'universalLaw', 1422355497952088065, 0, 1.00, 'Main', '/universalLaw', '普法宣传', 'md-beaker', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358166871609345, 'admin', '2021-08-03 08:46:28', 0, 'admin', '2021-08-03 08:52:59', NULL, 'legalCounsel', 1422355497952088065, 0, 2.00, 'Main', '/legalCounsel', '法律顾问', 'md-build', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358272438046720, 'admin', '2021-08-03 08:46:53', 0, 'admin', '2021-08-03 08:53:12', NULL, 'civilizedPractice', 1422355557112745984, 0, 1.00, 'Main', '/civilizedPractice', '文明实践', 'md-briefcase', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358347830661121, 'admin', '2021-08-03 08:47:11', 0, 'admin', '2021-08-03 08:53:26', NULL, 'familyStyle', 1422355557112745984, 0, 2.00, 'Main', '/familyStyle', '家风家训', 'md-bug', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358455477473281, 'admin', '2021-08-03 08:47:37', 0, 'admin', '2021-08-03 08:53:46', NULL, 'folkActivities', 1422355557112745984, 0, 3.00, 'Main', '/folkActivities', '民俗活动', 'ios-chatboxes', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358560397987841, 'admin', '2021-08-03 08:48:02', 0, 'admin', '2021-08-03 08:54:00', NULL, 'convenientService', 1422355621264625664, 0, 1.00, 'Main', '/convenientService', '便民服务中心', 'md-calendar', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358664722911233, 'admin', '2021-08-03 08:48:27', 0, 'admin', '2021-08-03 08:54:13', NULL, 'villageTravel', 1422355621264625664, 0, 2.00, 'Main', '/villageTravel', '乡村旅游', 'md-calculator', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422358804208685056, 'admin', '2021-08-03 08:49:00', 0, 'admin', '2021-08-03 08:54:32', NULL, 'villageStyle', 1422355621264625664, 0, 3.00, 'Main', '/villageStyle', '一村一品', 'md-construct', 1, '', 0, NULL, b'1', b'1', b'1'),
	(1422359074930036737, 'admin', '2021-08-03 08:50:04', 0, 'admin', '2021-08-03 08:50:04', NULL, 'dynamicBuild', 1422355875284258816, 0, 1.00, 'dj/dynamicBuild/index', 'dynamicBuild', '党建动态', 'md-construct', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359186267836417, 'admin', '2021-08-03 08:50:31', 0, 'admin', '2021-08-03 08:50:31', NULL, 'meeting', 1422356085288865793, 0, 1.00, 'dj/meeting/index', 'meeting', '党员管理', 'md-cog', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359273526136832, 'admin', '2021-08-03 08:50:52', 0, 'admin', '2021-08-03 08:50:52', NULL, 'onlineExamination', 1422356835742126080, 0, 1.00, 'dj/onlineExamination/index', 'onlineExamination', '在线考试', 'ios-color-fill', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359349963132929, 'admin', '2021-08-03 08:51:10', 0, 'admin', '2021-08-03 08:51:10', NULL, 'learningMaterials', 1422357545913290753, 0, 1.00, 'dj/learningMaterials/index', 'learningMaterials', '在线学习', 'md-color-filter', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359443181539329, 'admin', '2021-08-03 08:51:32', 0, 'admin', '2021-08-08 13:54:22', NULL, 'villageIntroduce', 1422357672317030401, 0, 1.00, 'zz/villageIntroduce/index', 'villageIntroduce', '村情介绍', 'md-color-fill', 2, '', 0, NULL, b'1', b'1', b'1'),
	(1422359514782502913, 'admin', '2021-08-03 08:51:49', 0, 'admin', '2021-08-03 08:51:49', NULL, 'taskDisclosure', 1422357764453306369, 0, 1.00, 'zz/taskDisclosure/index', 'taskDisclosure', '村务公开', 'ios-download', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359581258027008, 'admin', '2021-08-03 08:52:05', 0, 'admin', '2021-08-03 08:52:05', NULL, 'governanceEffectiveness', 1422357883705757697, 0, 1.00, 'zz/governanceEffectiveness/index', 'governanceEffectiveness', '治理成效', 'md-disc', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359657657274369, 'admin', '2021-08-03 08:52:23', 0, 'admin', '2021-08-03 08:52:23', NULL, 'villageAppointment', 1422357979881148416, 0, 1.00, 'zz/villageAppointment/index', 'villageAppointment', '村规民约', 'md-egg', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359739395870721, 'admin', '2021-08-03 08:52:43', 0, 'admin', '2021-08-03 08:52:43', NULL, 'universalLaw', 1422358091441246208, 0, 1.00, 'fz/universalLaw/index', 'universalLaw', '普法宣传', 'md-filing', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359806106275841, 'admin', '2021-08-03 08:52:59', 0, 'admin', '2021-08-03 08:52:59', NULL, 'legalCounsel', 1422358166871609345, 0, 1.00, 'fz/legalCounsel/index', 'legalCounsel', '法律顾问', 'md-funnel', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359861202653185, 'admin', '2021-08-03 08:53:12', 0, 'admin', '2021-08-03 08:53:12', NULL, 'civilizedPractice', 1422358272438046720, 0, 1.00, 'dz/civilizedPractice/index', 'civilizedPractice', '文明实践', 'md-flask', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422359921432858624, 'admin', '2021-08-03 08:53:26', 0, 'admin', '2021-08-03 08:53:26', NULL, 'familyStyle', 1422358347830661121, 0, 1.00, 'dz/familyStyle/index', 'familyStyle', '家风家训', 'md-git-compare', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422360004987588609, 'admin', '2021-08-03 08:53:46', 0, 'admin', '2021-08-03 08:53:46', NULL, 'folkActivities', 1422358455477473281, 0, 1.00, 'dz/folkActivities/index', 'folkActivities', '民俗活动', 'ios-ice-cream', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422360063498129409, 'admin', '2021-08-03 08:54:00', 0, 'admin', '2021-08-03 08:54:00', NULL, 'convenientService', 1422358560397987841, 0, 1.00, 'bm/convenientService/index', 'convenientService', '便民服务中心', 'ios-help-circle', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422360117520764929, 'admin', '2021-08-03 08:54:13', 0, 'admin', '2021-08-03 08:54:13', NULL, 'villageTravel', 1422358664722911233, 0, 1.00, 'bm/villageTravel/index', 'villageTravel', '乡村旅游', 'ios-list-box', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1422360196893773824, 'admin', '2021-08-03 08:54:32', 0, 'admin', '2021-08-03 08:54:32', NULL, 'villageStyle', 1422358804208685056, 0, 1.00, 'bm/villageStyle/index', 'villageStyle', '一村一品', 'md-medkit', 2, '', 0, NULL, b'1', b'1', b'0'),
	(1424247593885634561, 'admin', '2021-08-08 13:54:22', 0, 'admin', '2021-08-08 13:54:22', NULL, '', 1422359443181539329, 1, 1.00, '', '无', '管理员权限', '', 3, 'other', 0, NULL, b'1', b'1', b'0');
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;

-- 导出  表 village.t_quartz_job 结构
DROP TABLE IF EXISTS `t_quartz_job`;
CREATE TABLE IF NOT EXISTS `t_quartz_job` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `job_class_name` varchar(255) DEFAULT NULL,
  `parameter` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_quartz_job 的数据：~0 rows (大约)
DELETE FROM `t_quartz_job`;
/*!40000 ALTER TABLE `t_quartz_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_quartz_job` ENABLE KEYS */;

-- 导出  表 village.t_role 结构
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE IF NOT EXISTS `t_role` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `default_role` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `data_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_role 的数据：~2 rows (大约)
DELETE FROM `t_role`;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `name`, `del_flag`, `default_role`, `description`, `data_type`) VALUES
	(496138616573952, '', '2018-04-22 23:03:49', 'admin', '2018-11-15 23:02:59', 'ROLE_ADMIN', 0, NULL, '超级管理员 拥有所有权限', 0),
	(496138616573953, '', '2018-05-02 21:40:03', 'admin', '2018-11-01 22:59:48', 'ROLE_USER', 0, b'1', '普通注册用户 路过看看', 0);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;

-- 导出  表 village.t_role_department 结构
DROP TABLE IF EXISTS `t_role_department`;
CREATE TABLE IF NOT EXISTS `t_role_department` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `department_id` bigint(20) unsigned DEFAULT NULL,
  `role_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_role_department 的数据：~0 rows (大约)
DELETE FROM `t_role_department`;
/*!40000 ALTER TABLE `t_role_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_role_department` ENABLE KEYS */;

-- 导出  表 village.t_role_permission 结构
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE IF NOT EXISTS `t_role_permission` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `permission_id` bigint(20) unsigned DEFAULT NULL,
  `role_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_role_permission 的数据：~0 rows (大约)
DELETE FROM `t_role_permission`;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `permission_id`, `role_id`) VALUES
	(1424250239728095232, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 125909152017944576, 496138616573952),
	(1424250239728095233, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 5129710648430592, 496138616573952),
	(1424250239728095234, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 5129710648430593, 496138616573952),
	(1424250239728095235, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 15701400130424832, 496138616573952),
	(1424250239728095236, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16678126574637056, 496138616573952),
	(1424250239728095237, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 15701915807518720, 496138616573952),
	(1424250239728095238, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 15708892205944832, 496138616573952),
	(1424250239728095239, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16678447719911424, 496138616573952),
	(1424250239728095240, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 25014528525733888, 496138616573952),
	(1424250239728095241, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 56898976661639168, 496138616573952),
	(1424250239728095242, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 156365156580855808, 496138616573952),
	(1424250239728095243, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 40238597734928384, 496138616573952),
	(1424250239728095244, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 45235621697949696, 496138616573952),
	(1424250239728095245, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 45235787867885568, 496138616573952),
	(1424250239728095246, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 45235939278065664, 496138616573952),
	(1424250239728095247, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 56309618086776832, 496138616573952),
	(1424250239728095248, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 57212882168844288, 496138616573952),
	(1424250239728095249, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 61560041605435392, 496138616573952),
	(1424250239728095250, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 61560275261722624, 496138616573952),
	(1424250239728095251, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 61560480518377472, 496138616573952),
	(1424250239728095252, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 5129710648430594, 496138616573952),
	(1424250239728095253, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16687383932047360, 496138616573952),
	(1424250239728095254, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16689632049631232, 496138616573952),
	(1424250239728095255, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16689745006432256, 496138616573952),
	(1424250239728095256, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16689883993083904, 496138616573952),
	(1424250239728095257, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16690313745666048, 496138616573952),
	(1424250239728095258, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 5129710648430595, 496138616573952),
	(1424250239728095259, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16694861252005888, 496138616573952),
	(1424250239728095260, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16695107491205120, 496138616573952),
	(1424250239728095261, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 16695243126607872, 496138616573952),
	(1424250239728095262, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 75002207560273920, 496138616573952),
	(1424250239728095263, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76215889006956544, 496138616573952),
	(1424250239728095264, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76216071333351424, 496138616573952),
	(1424250239728095265, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76216264070008832, 496138616573952),
	(1424250239728095266, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76216459709124608, 496138616573952),
	(1424250239728095267, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76216594207870976, 496138616573952),
	(1424250239728095268, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 76216702639017984, 496138616573952),
	(1424250239728095269, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 58480609315524608, 496138616573952),
	(1424250239728095270, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 61394706252173312, 496138616573952),
	(1424250239728095271, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 61417744146370560, 496138616573952),
	(1424250239728095272, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422168282022154240, 496138616573952),
	(1424250239728095273, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 39915540965232640, 496138616573952),
	(1424250239728095274, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 41363147411427328, 496138616573952),
	(1424250239728095275, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 41363537456533504, 496138616573952),
	(1424250239728095276, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 41364927394353152, 496138616573952),
	(1424250239728095277, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422355389407694849, 496138616573952),
	(1424250239728095278, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422355875284258816, 496138616573952),
	(1424250239728095279, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422359074930036737, 496138616573952),
	(1424250239728095280, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422356085288865793, 496138616573952),
	(1424250239728095281, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422359186267836417, 496138616573952),
	(1424250239728095282, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422356835742126080, 496138616573952),
	(1424250239728095283, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422359273526136832, 496138616573952),
	(1424250239728095284, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422357545913290753, 496138616573952),
	(1424250239728095285, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422359349963132929, 496138616573952),
	(1424250239728095286, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422355444546015232, 496138616573952),
	(1424250239728095287, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422357672317030401, 496138616573952),
	(1424250239728095288, 'admin', '2021-08-08 14:04:53', 0, 'admin', '2021-08-08 14:04:53', 1422359443181539329, 496138616573952),
	(1424250239728095289, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1424247593885634561, 496138616573952),
	(1424250239728095290, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422357764453306369, 496138616573952),
	(1424250239728095291, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359514782502913, 496138616573952),
	(1424250239728095292, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422357883705757697, 496138616573952),
	(1424250239728095293, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359581258027008, 496138616573952),
	(1424250239728095294, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422357979881148416, 496138616573952),
	(1424250239728095295, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359657657274369, 496138616573952),
	(1424250239728095296, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422355497952088065, 496138616573952),
	(1424250239728095297, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358091441246208, 496138616573952),
	(1424250239728095298, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359739395870721, 496138616573952),
	(1424250239728095299, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358166871609345, 496138616573952),
	(1424250239728095300, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359806106275841, 496138616573952),
	(1424250239728095301, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422355557112745984, 496138616573952),
	(1424250239728095302, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358272438046720, 496138616573952),
	(1424250239728095303, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359861202653185, 496138616573952),
	(1424250239728095304, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358347830661121, 496138616573952),
	(1424250239728095305, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422359921432858624, 496138616573952),
	(1424250239728095306, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358455477473281, 496138616573952),
	(1424250239728095307, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422360004987588609, 496138616573952),
	(1424250239728095308, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422355621264625664, 496138616573952),
	(1424250239728095309, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358560397987841, 496138616573952),
	(1424250239728095310, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422360063498129409, 496138616573952),
	(1424250239728095311, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358664722911233, 496138616573952),
	(1424250239728095312, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422360117520764929, 496138616573952),
	(1424250239728095313, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422358804208685056, 496138616573952),
	(1424250239728095314, 'admin', '2021-08-08 14:04:54', 0, 'admin', '2021-08-08 14:04:54', 1422360196893773824, 496138616573952);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;

-- 导出  表 village.t_setting 结构
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE IF NOT EXISTS `t_setting` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `value` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_setting 的数据：~3 rows (大约)
DELETE FROM `t_setting`;
/*!40000 ALTER TABLE `t_setting` DISABLE KEYS */;
INSERT INTO `t_setting` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `value`) VALUES
	('LOCAL_OSS', 'admin', '2021-06-21 14:20:58', 0, 'admin', '2021-06-21 14:20:58', '{"serviceName":"LOCAL_OSS","endpoint":"127.0.0.1:9999/xboot/file/view","http":"http://","filePath":"D:/"}'),
	('OSS_USED', 'admin', '2021-06-21 14:20:58', 0, 'admin', '2021-06-21 14:20:58', 'LOCAL_OSS'),
	('OTHER_SETTING', 'admin', '2021-06-21 14:21:02', 0, 'admin', '2021-06-21 14:21:02', '{"domain":"http://127.0.0.1:8888","ssoDomain":"","blacklist":""}');
/*!40000 ALTER TABLE `t_setting` ENABLE KEYS */;

-- 导出  表 village.t_social 结构
DROP TABLE IF EXISTS `t_social`;
CREATE TABLE IF NOT EXISTS `t_social` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `platform` tinyint(1) unsigned DEFAULT NULL,
  `relate_username` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `relate_open_id` (`open_id`,`platform`) USING BTREE,
  UNIQUE KEY `relate_username` (`relate_username`,`platform`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_social 的数据：~0 rows (大约)
DELETE FROM `t_social`;
/*!40000 ALTER TABLE `t_social` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_social` ENABLE KEYS */;

-- 导出  表 village.t_stop_word 结构
DROP TABLE IF EXISTS `t_stop_word`;
CREATE TABLE IF NOT EXISTS `t_stop_word` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_stop_word 的数据：~0 rows (大约)
DELETE FROM `t_stop_word`;
/*!40000 ALTER TABLE `t_stop_word` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stop_word` ENABLE KEYS */;

-- 导出  表 village.t_task_disclosure 结构
DROP TABLE IF EXISTS `t_task_disclosure`;
CREATE TABLE IF NOT EXISTS `t_task_disclosure` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_task_disclosure 的数据：~0 rows (大约)
DELETE FROM `t_task_disclosure`;
/*!40000 ALTER TABLE `t_task_disclosure` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_task_disclosure` ENABLE KEYS */;

-- 导出  表 village.t_universal_law 结构
DROP TABLE IF EXISTS `t_universal_law`;
CREATE TABLE IF NOT EXISTS `t_universal_law` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_universal_law 的数据：~0 rows (大约)
DELETE FROM `t_universal_law`;
/*!40000 ALTER TABLE `t_universal_law` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_universal_law` ENABLE KEYS */;

-- 导出  表 village.t_user 结构
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `department_id` bigint(20) unsigned DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `pass_strength` varchar(2) DEFAULT NULL,
  `department_title` varchar(255) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_user 的数据：~0 rows (大约)
DELETE FROM `t_user`;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `address`, `avatar`, `description`, `email`, `mobile`, `nickname`, `password`, `sex`, `status`, `type`, `username`, `del_flag`, `department_id`, `street`, `pass_strength`, `department_title`, `birth`) VALUES
	(682265633886208, '', '2018-05-01 16:13:51', 'admin', '2020-04-12 22:03:47', '北京市,市辖区,东城区', 'https://ooo.0o0.ooo/2019/04/28/5cc5a71a6e3b6.png', '我是大帅逼', 'admin@exrick.cn', '18782059031', '管理员', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '男', 0, 1, 'admin', 0, 40322777781112832, '天府1街', '弱', '总部', '2020-04-15 00:00:00.000000');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

-- 导出  表 village.t_user_role 结构
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE IF NOT EXISTS `t_user_role` (
  `id` bigint(20) unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint(20) unsigned DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_user_role 的数据：~0 rows (大约)
DELETE FROM `t_user_role`;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `role_id`, `user_id`) VALUES
	(1254427593457930240, 'admin', '2020-04-26 23:10:19', 0, 'admin', '2020-04-26 23:10:19', 496138616573952, 682265633886208);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;

-- 导出  表 village.t_village_appointment 结构
DROP TABLE IF EXISTS `t_village_appointment`;
CREATE TABLE IF NOT EXISTS `t_village_appointment` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_village_appointment 的数据：~0 rows (大约)
DELETE FROM `t_village_appointment`;
/*!40000 ALTER TABLE `t_village_appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_village_appointment` ENABLE KEYS */;

-- 导出  表 village.t_village_introduce 结构
DROP TABLE IF EXISTS `t_village_introduce`;
CREATE TABLE IF NOT EXISTS `t_village_introduce` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `audit_status` int(11) NOT NULL,
  `content` varchar(16384) DEFAULT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT '',
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_village_introduce 的数据：~1 rows (大约)
DELETE FROM `t_village_introduce`;
/*!40000 ALTER TABLE `t_village_introduce` DISABLE KEYS */;
INSERT INTO `t_village_introduce` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `audit_status`, `content`, `create_date`, `level`, `remark`, `title`, `type`, `url`) VALUES
	('1424207211793616896', 'admin', '2021-08-08 11:13:54.470000', 0, 'admin', '2021-08-08 15:02:01.474000', 1, '<p style="text-align: center;"><span style="font-size: 36px;"><strong>&nbsp;XX村介绍</strong></span></p><p><br/></p><p>**村位于乡政府所在地东部，距乡政府所在地2公里，距石阡县城38公里，内属中亚热带温暖湿润气候，四季分明，夏热冬凉，年平均降水量1100毫米。全**村共有5个**村民组，665户，1838人，是一个以侗族为主的多民族杂居**村寨。全**村总面积17.8平方公里，耕地面积为2345亩，森林覆盖率达36%，主要农作物有烤烟、水稻、玉米等。</p><p style="text-align: center;"><img src="https://artskyhome.com:8082/ueditor/ueditor/jsp/upload/image/20210808/1628392424368003298.png" title="1628392424368003298.png" alt="微信截图_20210808111121.png"/>&nbsp;&nbsp;&nbsp;</p><p>**村内建有**村级小学、卫生室、**村委会，但大部分**村民组为砼硬路面，无公共厕所，没有垃圾池，无路灯，没有公共活动广场，公共服务设施欠缺，极大地影响着农民群众生活质量的提高。<br/>&nbsp;&nbsp;&nbsp;本次整**村推进一事一议项目共39个，受益人数1083人，总投入276万元，其中申请省级财政奖补152万元，县级财政预算安排62万元，县级整合62万元。水利类财政奖补7个项目，投入资金106.4万元；交通类18个项目，投入95.6万元；环卫类5个项目，投入3.8万元；其它类9个，投入70.2万元。<br/>&nbsp;&nbsp;&nbsp;通过该项目的实施，可改造农**村生活环境，完善各种基础设施建设，促进农**村产业快速发展，创新**村庄管理机制，改变农民精神风貌，大力打造新经济、新生活、新**村庄、新机制、新风尚的新型农**村，构建和谐社会。同时带动养殖业、种植业、传统农业等各项产业的全面发展，使农民的年人均纯收入由原来的6500元增加到9000元左右。</p>', '2021-08-08 11:13:54', 10, '测试', 'XX村庄介绍', '0', NULL),
	('1424239586393067521', 'admin', '2021-08-08 13:22:33.169000', 0, 'admin', '2021-08-08 15:01:57.780000', 2, '', '2021-08-08 13:22:33', 10, '测试', '外链文章测试', '1', 'http://baijiahao.baidu.com/s?id=1707427551447931571');
/*!40000 ALTER TABLE `t_village_introduce` ENABLE KEYS */;

-- 导出  表 village.t_village_style 结构
DROP TABLE IF EXISTS `t_village_style`;
CREATE TABLE IF NOT EXISTS `t_village_style` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_village_style 的数据：~0 rows (大约)
DELETE FROM `t_village_style`;
/*!40000 ALTER TABLE `t_village_style` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_village_style` ENABLE KEYS */;

-- 导出  表 village.t_village_travel 结构
DROP TABLE IF EXISTS `t_village_travel`;
CREATE TABLE IF NOT EXISTS `t_village_travel` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  village.t_village_travel 的数据：~0 rows (大约)
DELETE FROM `t_village_travel`;
/*!40000 ALTER TABLE `t_village_travel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_village_travel` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
