/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : wbdata

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-12-28 23:39:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `document_explorer`
-- ----------------------------
DROP TABLE IF EXISTS `document_explorer`;
CREATE TABLE `document_explorer` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `introduction` varchar(255) DEFAULT NULL,
  `document_name` varchar(255) DEFAULT NULL,
  `document_path` varchar(255) DEFAULT NULL,
  `distribution_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `document_version` varchar(255) DEFAULT NULL,
  `document_size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document_explorer
-- ----------------------------
INSERT INTO `document_explorer` VALUES ('1', '操作说明', '模具结构', '/mould/201612280001.pdf', '2016-12-28 10:40:43', '1.0', '2000');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enum_data
-- ----------------------------
INSERT INTO `enum_data` VALUES ('1', '1', null, '0', '停机', '1');
INSERT INTO `enum_data` VALUES ('2', '1', null, '2', '换模', '1');
INSERT INTO `enum_data` VALUES ('3', '1', null, '1', '生产', '1');
INSERT INTO `enum_data` VALUES ('4', '1', null, '3', '待人开机', '1');
INSERT INTO `enum_data` VALUES ('5', '1', null, '4', '待料', '1');
INSERT INTO `enum_data` VALUES ('6', '1', null, '5', '修模', '1');
INSERT INTO `enum_data` VALUES ('7', '1', null, '6', '试产', '1');
INSERT INTO `enum_data` VALUES ('8', '1', null, '7', '修机', '1');
INSERT INTO `enum_data` VALUES ('9', '1', null, '8', '保养', '1');
INSERT INTO `enum_data` VALUES ('10', '1', null, '9', '调机', '1');

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
  `work_order_no` varchar(255) DEFAULT NULL,
  `mould_no` varchar(255) DEFAULT NULL,
  `part_code` varchar(255) DEFAULT NULL,
  `part_name` varchar(255) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `status_desc` varchar(20) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_serial_no_status` (`status`,`serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment_status
-- ----------------------------
INSERT INTO `equipment_status` VALUES ('1', '6b95cb3a-cb4e-11e6-952f-507b9db02f96', 'MOULD5', '1', '类型1', '10000000', '20000000', '30000000', '螺丝钉', '1', '生产', '2016-12-28 18:39:29');
INSERT INTO `equipment_status` VALUES ('2', '6b95cb3a-cb4e-11e6-952f-507b9db02f94', 'MOULD3', '1', '类型1', '10000001', '20000001', '30000001', '手机外壳', '3', '待料', '2016-12-28 10:33:21');
INSERT INTO `equipment_status` VALUES ('3', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '类型1', '10000001', '20000001', '30000001', '手机外壳', '2', '换模', '2016-12-28 23:37:47');
INSERT INTO `equipment_status` VALUES ('4', '6b95cb3a-cb4e-11e6-952f-507b9db02f93', 'MOULD2', '1', '类型1', '10000001', '20000001', '30000001', '手机外壳', '3', '待料', '2016-12-28 10:33:28');

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
  `work_order_no` varchar(255) DEFAULT NULL,
  `part_code` varchar(255) DEFAULT NULL,
  `part_name` varchar(255) DEFAULT NULL,
  `mould_no` varchar(255) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `status_desc` varchar(20) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=355 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of equipment_status_log
-- ----------------------------
INSERT INTO `equipment_status_log` VALUES ('169', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000000', '30000000', '螺丝钉', '20000000', '0', '停机', '2016-12-28 21:37:09');
INSERT INTO `equipment_status_log` VALUES ('170', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000000', '30000000', '螺丝钉', '20000000', '0', '停机', '2016-12-28 21:37:56');
INSERT INTO `equipment_status_log` VALUES ('171', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000000', '30000000', '螺丝钉', '20000000', '0', '停机', '2016-12-28 21:38:00');
INSERT INTO `equipment_status_log` VALUES ('172', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000000', '30000000', '螺丝钉', '20000000', '0', '停机', '2016-12-28 21:38:03');
INSERT INTO `equipment_status_log` VALUES ('173', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:48:58');
INSERT INTO `equipment_status_log` VALUES ('174', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:49:05');
INSERT INTO `equipment_status_log` VALUES ('175', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:08');
INSERT INTO `equipment_status_log` VALUES ('176', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:09');
INSERT INTO `equipment_status_log` VALUES ('177', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:10');
INSERT INTO `equipment_status_log` VALUES ('178', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:11');
INSERT INTO `equipment_status_log` VALUES ('179', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:12');
INSERT INTO `equipment_status_log` VALUES ('180', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:12');
INSERT INTO `equipment_status_log` VALUES ('181', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:13');
INSERT INTO `equipment_status_log` VALUES ('182', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:49:52');
INSERT INTO `equipment_status_log` VALUES ('183', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:49:55');
INSERT INTO `equipment_status_log` VALUES ('184', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:49:59');
INSERT INTO `equipment_status_log` VALUES ('185', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:55:40');
INSERT INTO `equipment_status_log` VALUES ('186', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:55:44');
INSERT INTO `equipment_status_log` VALUES ('187', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:56:53');
INSERT INTO `equipment_status_log` VALUES ('188', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 21:56:56');
INSERT INTO `equipment_status_log` VALUES ('189', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:56:59');
INSERT INTO `equipment_status_log` VALUES ('190', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:00');
INSERT INTO `equipment_status_log` VALUES ('191', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:01');
INSERT INTO `equipment_status_log` VALUES ('192', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:02');
INSERT INTO `equipment_status_log` VALUES ('193', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:02');
INSERT INTO `equipment_status_log` VALUES ('194', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:03');
INSERT INTO `equipment_status_log` VALUES ('195', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:03');
INSERT INTO `equipment_status_log` VALUES ('196', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:57:03');
INSERT INTO `equipment_status_log` VALUES ('197', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:05');
INSERT INTO `equipment_status_log` VALUES ('198', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:57:06');
INSERT INTO `equipment_status_log` VALUES ('199', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:07');
INSERT INTO `equipment_status_log` VALUES ('200', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:07');
INSERT INTO `equipment_status_log` VALUES ('201', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:07');
INSERT INTO `equipment_status_log` VALUES ('202', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:57:08');
INSERT INTO `equipment_status_log` VALUES ('203', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:58:49');
INSERT INTO `equipment_status_log` VALUES ('204', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:58:50');
INSERT INTO `equipment_status_log` VALUES ('205', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:58:55');
INSERT INTO `equipment_status_log` VALUES ('206', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:01');
INSERT INTO `equipment_status_log` VALUES ('207', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:03');
INSERT INTO `equipment_status_log` VALUES ('208', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:05');
INSERT INTO `equipment_status_log` VALUES ('209', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:12');
INSERT INTO `equipment_status_log` VALUES ('210', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:59:14');
INSERT INTO `equipment_status_log` VALUES ('211', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:59:22');
INSERT INTO `equipment_status_log` VALUES ('212', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:25');
INSERT INTO `equipment_status_log` VALUES ('213', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 21:59:26');
INSERT INTO `equipment_status_log` VALUES ('214', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 21:59:29');
INSERT INTO `equipment_status_log` VALUES ('215', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '', '', '', '', '9', '调机', '2016-12-28 22:01:55');
INSERT INTO `equipment_status_log` VALUES ('216', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '', '', '', '', '9', '调机', '2016-12-28 22:02:03');
INSERT INTO `equipment_status_log` VALUES ('217', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:02:07');
INSERT INTO `equipment_status_log` VALUES ('218', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:02:09');
INSERT INTO `equipment_status_log` VALUES ('219', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '', '', '', '', '9', '调机', '2016-12-28 22:03:18');
INSERT INTO `equipment_status_log` VALUES ('220', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:02:12');
INSERT INTO `equipment_status_log` VALUES ('221', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '', '', '', '', '9', '调机', '2016-12-28 22:03:22');
INSERT INTO `equipment_status_log` VALUES ('222', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:03:32');
INSERT INTO `equipment_status_log` VALUES ('223', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:03:32');
INSERT INTO `equipment_status_log` VALUES ('224', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:04:12');
INSERT INTO `equipment_status_log` VALUES ('225', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:04:15');
INSERT INTO `equipment_status_log` VALUES ('226', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:14:20');
INSERT INTO `equipment_status_log` VALUES ('227', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:14:22');
INSERT INTO `equipment_status_log` VALUES ('228', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:14:23');
INSERT INTO `equipment_status_log` VALUES ('229', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:25');
INSERT INTO `equipment_status_log` VALUES ('230', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:28');
INSERT INTO `equipment_status_log` VALUES ('231', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:29');
INSERT INTO `equipment_status_log` VALUES ('232', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:30');
INSERT INTO `equipment_status_log` VALUES ('233', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:31');
INSERT INTO `equipment_status_log` VALUES ('234', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:32');
INSERT INTO `equipment_status_log` VALUES ('235', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:33');
INSERT INTO `equipment_status_log` VALUES ('236', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:37');
INSERT INTO `equipment_status_log` VALUES ('237', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:39');
INSERT INTO `equipment_status_log` VALUES ('238', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:40');
INSERT INTO `equipment_status_log` VALUES ('239', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:41');
INSERT INTO `equipment_status_log` VALUES ('240', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:43');
INSERT INTO `equipment_status_log` VALUES ('241', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:43');
INSERT INTO `equipment_status_log` VALUES ('242', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:44');
INSERT INTO `equipment_status_log` VALUES ('243', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:45');
INSERT INTO `equipment_status_log` VALUES ('244', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:47');
INSERT INTO `equipment_status_log` VALUES ('245', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:48');
INSERT INTO `equipment_status_log` VALUES ('246', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:49');
INSERT INTO `equipment_status_log` VALUES ('247', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:49');
INSERT INTO `equipment_status_log` VALUES ('248', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:49');
INSERT INTO `equipment_status_log` VALUES ('249', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:49');
INSERT INTO `equipment_status_log` VALUES ('250', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:50');
INSERT INTO `equipment_status_log` VALUES ('251', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:51');
INSERT INTO `equipment_status_log` VALUES ('252', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:52');
INSERT INTO `equipment_status_log` VALUES ('253', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:52');
INSERT INTO `equipment_status_log` VALUES ('254', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:53');
INSERT INTO `equipment_status_log` VALUES ('255', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:54');
INSERT INTO `equipment_status_log` VALUES ('256', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:55');
INSERT INTO `equipment_status_log` VALUES ('257', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:56');
INSERT INTO `equipment_status_log` VALUES ('258', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:57');
INSERT INTO `equipment_status_log` VALUES ('259', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:14:58');
INSERT INTO `equipment_status_log` VALUES ('260', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:14:59');
INSERT INTO `equipment_status_log` VALUES ('261', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:15:00');
INSERT INTO `equipment_status_log` VALUES ('262', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:15:01');
INSERT INTO `equipment_status_log` VALUES ('263', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:15:02');
INSERT INTO `equipment_status_log` VALUES ('264', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:15:03');
INSERT INTO `equipment_status_log` VALUES ('265', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:15:05');
INSERT INTO `equipment_status_log` VALUES ('266', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:15:07');
INSERT INTO `equipment_status_log` VALUES ('267', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:15:07');
INSERT INTO `equipment_status_log` VALUES ('268', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 22:15:07');
INSERT INTO `equipment_status_log` VALUES ('269', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:15:17');
INSERT INTO `equipment_status_log` VALUES ('270', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:15:20');
INSERT INTO `equipment_status_log` VALUES ('271', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 22:15:23');
INSERT INTO `equipment_status_log` VALUES ('272', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('273', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('274', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('275', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('276', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('277', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('278', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:09');
INSERT INTO `equipment_status_log` VALUES ('279', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('280', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('281', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('282', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('283', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('284', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 22:22:10');
INSERT INTO `equipment_status_log` VALUES ('285', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:21:13');
INSERT INTO `equipment_status_log` VALUES ('286', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:21:20');
INSERT INTO `equipment_status_log` VALUES ('287', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:22');
INSERT INTO `equipment_status_log` VALUES ('288', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:24');
INSERT INTO `equipment_status_log` VALUES ('289', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:29');
INSERT INTO `equipment_status_log` VALUES ('290', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:34');
INSERT INTO `equipment_status_log` VALUES ('291', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:35');
INSERT INTO `equipment_status_log` VALUES ('292', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:35');
INSERT INTO `equipment_status_log` VALUES ('293', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:37');
INSERT INTO `equipment_status_log` VALUES ('294', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:42');
INSERT INTO `equipment_status_log` VALUES ('295', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:43');
INSERT INTO `equipment_status_log` VALUES ('296', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:45');
INSERT INTO `equipment_status_log` VALUES ('297', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:45');
INSERT INTO `equipment_status_log` VALUES ('298', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:46');
INSERT INTO `equipment_status_log` VALUES ('299', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:47');
INSERT INTO `equipment_status_log` VALUES ('300', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:48');
INSERT INTO `equipment_status_log` VALUES ('301', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:50');
INSERT INTO `equipment_status_log` VALUES ('302', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:50');
INSERT INTO `equipment_status_log` VALUES ('303', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:51');
INSERT INTO `equipment_status_log` VALUES ('304', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:21:53');
INSERT INTO `equipment_status_log` VALUES ('305', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:21:54');
INSERT INTO `equipment_status_log` VALUES ('306', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:21:56');
INSERT INTO `equipment_status_log` VALUES ('307', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:21:58');
INSERT INTO `equipment_status_log` VALUES ('308', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:22:01');
INSERT INTO `equipment_status_log` VALUES ('309', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:22:03');
INSERT INTO `equipment_status_log` VALUES ('310', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '4', '待料', '2016-12-28 23:22:05');
INSERT INTO `equipment_status_log` VALUES ('311', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:09');
INSERT INTO `equipment_status_log` VALUES ('312', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:22:10');
INSERT INTO `equipment_status_log` VALUES ('313', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:22:15');
INSERT INTO `equipment_status_log` VALUES ('314', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:18');
INSERT INTO `equipment_status_log` VALUES ('315', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:22:20');
INSERT INTO `equipment_status_log` VALUES ('316', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:21');
INSERT INTO `equipment_status_log` VALUES ('317', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:24');
INSERT INTO `equipment_status_log` VALUES ('318', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:34');
INSERT INTO `equipment_status_log` VALUES ('319', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:38');
INSERT INTO `equipment_status_log` VALUES ('320', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:38');
INSERT INTO `equipment_status_log` VALUES ('321', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:40');
INSERT INTO `equipment_status_log` VALUES ('322', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:44');
INSERT INTO `equipment_status_log` VALUES ('323', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:46');
INSERT INTO `equipment_status_log` VALUES ('324', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:47');
INSERT INTO `equipment_status_log` VALUES ('325', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:48');
INSERT INTO `equipment_status_log` VALUES ('326', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:49');
INSERT INTO `equipment_status_log` VALUES ('327', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:51');
INSERT INTO `equipment_status_log` VALUES ('328', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:55');
INSERT INTO `equipment_status_log` VALUES ('329', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:22:55');
INSERT INTO `equipment_status_log` VALUES ('330', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:57');
INSERT INTO `equipment_status_log` VALUES ('331', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:22:58');
INSERT INTO `equipment_status_log` VALUES ('332', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:22:59');
INSERT INTO `equipment_status_log` VALUES ('333', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:00');
INSERT INTO `equipment_status_log` VALUES ('334', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:01');
INSERT INTO `equipment_status_log` VALUES ('335', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:02');
INSERT INTO `equipment_status_log` VALUES ('336', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:03');
INSERT INTO `equipment_status_log` VALUES ('337', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:04');
INSERT INTO `equipment_status_log` VALUES ('338', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:05');
INSERT INTO `equipment_status_log` VALUES ('339', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:06');
INSERT INTO `equipment_status_log` VALUES ('340', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:07');
INSERT INTO `equipment_status_log` VALUES ('341', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:08');
INSERT INTO `equipment_status_log` VALUES ('342', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:09');
INSERT INTO `equipment_status_log` VALUES ('343', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:10');
INSERT INTO `equipment_status_log` VALUES ('344', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '3', '待人开机', '2016-12-28 23:23:11');
INSERT INTO `equipment_status_log` VALUES ('345', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '0', '停机', '2016-12-28 23:23:12');
INSERT INTO `equipment_status_log` VALUES ('346', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:23:30');
INSERT INTO `equipment_status_log` VALUES ('347', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:23:38');
INSERT INTO `equipment_status_log` VALUES ('348', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:29:15');
INSERT INTO `equipment_status_log` VALUES ('349', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:29:38');
INSERT INTO `equipment_status_log` VALUES ('350', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:29:42');
INSERT INTO `equipment_status_log` VALUES ('351', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', 'null', 'null', 'null', 'null', '2', '换模', '2016-12-28 23:33:25');
INSERT INTO `equipment_status_log` VALUES ('352', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000001', '30000001', '手机外壳', '20000001', '2', '换模', '2016-12-28 23:37:38');
INSERT INTO `equipment_status_log` VALUES ('353', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000000', '30000000', '螺丝钉', '20000000', '2', '换模', '2016-12-28 23:37:45');
INSERT INTO `equipment_status_log` VALUES ('354', '6b95cb3a-cb4e-11e6-952f-507b9db02f92', 'MOULD1', '1', '', '10000001', '30000001', '手机外壳', '20000001', '2', '换模', '2016-12-28 23:37:47');

-- ----------------------------
-- Table structure for `work_order`
-- ----------------------------
DROP TABLE IF EXISTS `work_order`;
CREATE TABLE `work_order` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `work_order_no` varchar(255) DEFAULT NULL,
  `mould_no` varchar(255) DEFAULT NULL,
  `part_code` varchar(255) DEFAULT NULL,
  `part_name` varchar(255) DEFAULT NULL,
  `cycle_time` int(11) DEFAULT NULL,
  `plan_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_work_order_no` (`work_order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_order
-- ----------------------------
INSERT INTO `work_order` VALUES ('1', '10000000', '20000000', '30000000', '螺丝钉', '10', '100');
INSERT INTO `work_order` VALUES ('2', '10000001', '20000001', '30000001', '手机外壳', '10', '100');
