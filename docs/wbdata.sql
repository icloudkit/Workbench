/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : wbdata

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-12-28 00:10:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `enum_data`
-- ----------------------------
DROP TABLE IF EXISTS `enum_data`;
CREATE TABLE `enum_data` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `enum_type` varchar(30) DEFAULT NULL,
  `enum_type_desc` varchar(30) DEFAULT NULL,
  `enum_key` varchar(30) DEFAULT NULL,
  `enum_name` varchar(30) DEFAULT NULL,
  `is_enable` tinyint(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enum_data
-- ----------------------------
INSERT INTO `enum_data` VALUES ('1', '1', null, '0', '停机', '1');
INSERT INTO `enum_data` VALUES ('2', '1', null, '1', '生产', '1');
INSERT INTO `enum_data` VALUES ('3', '1', null, '2', '待人开机', '1');
INSERT INTO `enum_data` VALUES ('4', '1', null, '3', '待料', '1');
INSERT INTO `enum_data` VALUES ('5', '1', null, '4', '修模', '1');
INSERT INTO `enum_data` VALUES ('6', '1', null, '5', '试产', '1');
INSERT INTO `enum_data` VALUES ('7', '1', null, '6', '修机', '1');
INSERT INTO `enum_data` VALUES ('8', '1', null, '7', '保养', '1');
INSERT INTO `enum_data` VALUES ('9', '1', null, '8', '调机', '1');

-- ----------------------------
-- Table structure for `equipment`
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `local_addr` varchar(30) DEFAULT NULL,
  `serial_no` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_local_addr` (`local_addr`),
  UNIQUE KEY `idx_serial_no` (`serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('1', 'MOULD1', '192.168.0.105', '6b95cb3a-cb4e-11e6-952f-507b9db02f92');
INSERT INTO `equipment` VALUES ('2', 'MOULD2', '192.168.0.18', '6b95cb3a-cb4e-11e6-952f-507b9db02f93');
INSERT INTO `equipment` VALUES ('3', 'MOULD3', '192.168.1.169', '6b95cb3a-cb4e-11e6-952f-507b9db02f94');
INSERT INTO `equipment` VALUES ('4', 'MOULD4', '127.0.0.1', '6b95cb3a-cb4e-11e6-952f-507b9db02f95');
INSERT INTO `equipment` VALUES ('5', 'MOULD5', '192.168.1.9', '6b95cb3a-cb4e-11e6-952f-507b9db02f96');

-- ----------------------------
-- Table structure for `equipment_status`
-- ----------------------------
DROP TABLE IF EXISTS `equipment_status`;
CREATE TABLE `equipment_status` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(60) DEFAULT NULL,
  `equipment_name` varchar(60) DEFAULT NULL,
  `type` tinyint(3) DEFAULT NULL,
  `type_desc` varchar(30) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `status_desc` varchar(20) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_serial_no_status` (`status`,`serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment_status
-- ----------------------------
INSERT INTO `equipment_status` VALUES ('1', '6b95cb3a-cb4e-11e6-952f-507b9db02f96', 'MOULD5', '1', '类型1', '3', '待料', '2016-12-27 21:32:55');
INSERT INTO `equipment_status` VALUES ('4', '6b95cb3a-cb4e-11e6-952f-507b9db02f94', 'MOULD3', '1', '类型1', '3', '待料', '2016-12-27 21:32:54');
INSERT INTO `equipment_status` VALUES ('5', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '类型1', '0', '停机', '2016-12-27 23:54:38');
INSERT INTO `equipment_status` VALUES ('6', '6b95cb3a-cb4e-11e6-952f-507b9db02f93', 'MOULD2', '1', '类型1', '3', '待料', '2016-12-27 21:33:58');

-- ----------------------------
-- Table structure for `equipment_status_log`
-- ----------------------------
DROP TABLE IF EXISTS `equipment_status_log`;
CREATE TABLE `equipment_status_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(60) DEFAULT NULL,
  `equipment_name` varchar(60) DEFAULT NULL,
  `type` tinyint(3) DEFAULT NULL,
  `type_desc` varchar(30) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `status_desc` varchar(20) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment_status_log
-- ----------------------------
INSERT INTO `equipment_status_log` VALUES ('20', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:27:17');
INSERT INTO `equipment_status_log` VALUES ('21', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:27:17');
INSERT INTO `equipment_status_log` VALUES ('22', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:27:18');

