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
INSERT INTO `equipment_status_log` VALUES ('23', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '7', '保养', '2016-12-27 23:27:18');
INSERT INTO `equipment_status_log` VALUES ('24', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '3', '待料', '2016-12-27 23:27:19');
INSERT INTO `equipment_status_log` VALUES ('25', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:27:19');
INSERT INTO `equipment_status_log` VALUES ('26', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:27:19');
INSERT INTO `equipment_status_log` VALUES ('27', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:27:20');
INSERT INTO `equipment_status_log` VALUES ('28', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:27:20');
INSERT INTO `equipment_status_log` VALUES ('29', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:32:04');
INSERT INTO `equipment_status_log` VALUES ('30', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:32:07');
INSERT INTO `equipment_status_log` VALUES ('31', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:37:09');
INSERT INTO `equipment_status_log` VALUES ('32', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:37:13');
INSERT INTO `equipment_status_log` VALUES ('33', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:37:14');
INSERT INTO `equipment_status_log` VALUES ('34', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:37:16');
INSERT INTO `equipment_status_log` VALUES ('35', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:37:18');
INSERT INTO `equipment_status_log` VALUES ('36', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:37:19');
INSERT INTO `equipment_status_log` VALUES ('37', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:37:22');
INSERT INTO `equipment_status_log` VALUES ('38', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:38:09');
INSERT INTO `equipment_status_log` VALUES ('39', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '3', '待料', '2016-12-27 23:38:09');
INSERT INTO `equipment_status_log` VALUES ('40', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:38:11');
INSERT INTO `equipment_status_log` VALUES ('41', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:38:12');
INSERT INTO `equipment_status_log` VALUES ('42', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:38:13');
INSERT INTO `equipment_status_log` VALUES ('43', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:38:13');
INSERT INTO `equipment_status_log` VALUES ('44', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:38:14');
INSERT INTO `equipment_status_log` VALUES ('45', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:38:15');
INSERT INTO `equipment_status_log` VALUES ('46', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:38:15');
INSERT INTO `equipment_status_log` VALUES ('47', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:52:32');
INSERT INTO `equipment_status_log` VALUES ('48', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:52:32');
INSERT INTO `equipment_status_log` VALUES ('49', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:32');
INSERT INTO `equipment_status_log` VALUES ('50', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:36');
INSERT INTO `equipment_status_log` VALUES ('51', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:36');
INSERT INTO `equipment_status_log` VALUES ('52', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:37');
INSERT INTO `equipment_status_log` VALUES ('53', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:37');
INSERT INTO `equipment_status_log` VALUES ('54', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '3', '待料', '2016-12-27 23:52:38');
INSERT INTO `equipment_status_log` VALUES ('55', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:52:39');
INSERT INTO `equipment_status_log` VALUES ('56', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '7', '保养', '2016-12-27 23:52:42');
INSERT INTO `equipment_status_log` VALUES ('57', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:43');
INSERT INTO `equipment_status_log` VALUES ('58', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:52:44');
INSERT INTO `equipment_status_log` VALUES ('59', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:45');
INSERT INTO `equipment_status_log` VALUES ('60', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:52:46');
INSERT INTO `equipment_status_log` VALUES ('61', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:52:47');
INSERT INTO `equipment_status_log` VALUES ('62', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:50');
INSERT INTO `equipment_status_log` VALUES ('63', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:51');
INSERT INTO `equipment_status_log` VALUES ('64', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:52:51');
INSERT INTO `equipment_status_log` VALUES ('65', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:52:52');
INSERT INTO `equipment_status_log` VALUES ('66', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:52');
INSERT INTO `equipment_status_log` VALUES ('67', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:52:54');
INSERT INTO `equipment_status_log` VALUES ('68', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:52:54');
INSERT INTO `equipment_status_log` VALUES ('69', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:55');
INSERT INTO `equipment_status_log` VALUES ('70', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '6', '修机', '2016-12-27 23:52:55');
INSERT INTO `equipment_status_log` VALUES ('71', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:55');
INSERT INTO `equipment_status_log` VALUES ('72', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '1', '生产', '2016-12-27 23:52:56');
INSERT INTO `equipment_status_log` VALUES ('73', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '4', '修模', '2016-12-27 23:52:57');
INSERT INTO `equipment_status_log` VALUES ('74', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:52:57');
INSERT INTO `equipment_status_log` VALUES ('75', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:58');
INSERT INTO `equipment_status_log` VALUES ('76', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:58');
INSERT INTO `equipment_status_log` VALUES ('77', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:52:58');
INSERT INTO `equipment_status_log` VALUES ('78', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:59');
INSERT INTO `equipment_status_log` VALUES ('79', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:59');
INSERT INTO `equipment_status_log` VALUES ('80', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:52:59');
INSERT INTO `equipment_status_log` VALUES ('81', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:52:59');
INSERT INTO `equipment_status_log` VALUES ('82', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:00');
INSERT INTO `equipment_status_log` VALUES ('83', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:00');
INSERT INTO `equipment_status_log` VALUES ('84', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:00');
INSERT INTO `equipment_status_log` VALUES ('85', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:01');
INSERT INTO `equipment_status_log` VALUES ('86', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:01');
INSERT INTO `equipment_status_log` VALUES ('87', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:01');
INSERT INTO `equipment_status_log` VALUES ('88', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:02');
INSERT INTO `equipment_status_log` VALUES ('89', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:03');
INSERT INTO `equipment_status_log` VALUES ('90', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:03');
INSERT INTO `equipment_status_log` VALUES ('91', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:04');
INSERT INTO `equipment_status_log` VALUES ('92', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:04');
INSERT INTO `equipment_status_log` VALUES ('93', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:08');
INSERT INTO `equipment_status_log` VALUES ('94', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:08');
INSERT INTO `equipment_status_log` VALUES ('95', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:09');
INSERT INTO `equipment_status_log` VALUES ('96', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:09');
INSERT INTO `equipment_status_log` VALUES ('97', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:11');
INSERT INTO `equipment_status_log` VALUES ('98', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:11');
INSERT INTO `equipment_status_log` VALUES ('99', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:12');
INSERT INTO `equipment_status_log` VALUES ('100', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:12');
INSERT INTO `equipment_status_log` VALUES ('101', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:12');
INSERT INTO `equipment_status_log` VALUES ('102', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:13');
INSERT INTO `equipment_status_log` VALUES ('103', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:13');
INSERT INTO `equipment_status_log` VALUES ('104', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:14');
INSERT INTO `equipment_status_log` VALUES ('105', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:14');
INSERT INTO `equipment_status_log` VALUES ('106', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:14');
INSERT INTO `equipment_status_log` VALUES ('107', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:15');
INSERT INTO `equipment_status_log` VALUES ('108', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:15');
INSERT INTO `equipment_status_log` VALUES ('109', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:16');
INSERT INTO `equipment_status_log` VALUES ('110', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:16');
INSERT INTO `equipment_status_log` VALUES ('111', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:18');
INSERT INTO `equipment_status_log` VALUES ('112', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:22');
INSERT INTO `equipment_status_log` VALUES ('113', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:22');
INSERT INTO `equipment_status_log` VALUES ('114', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '8', '调机', '2016-12-27 23:53:23');
INSERT INTO `equipment_status_log` VALUES ('115', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:25');
INSERT INTO `equipment_status_log` VALUES ('116', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '5', '试产', '2016-12-27 23:53:30');
INSERT INTO `equipment_status_log` VALUES ('117', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '2', '待人开机', '2016-12-27 23:53:31');
INSERT INTO `equipment_status_log` VALUES ('118', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '0', '停机', '2016-12-27 23:53:33');
