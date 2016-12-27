/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : wbdata

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-12-26 23:45:04
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
-- Table structure for `mould`
-- ----------------------------
DROP TABLE IF EXISTS `mould`;
CREATE TABLE `mould` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `local_addr` varchar(30) DEFAULT NULL,
  `serial_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mould
-- ----------------------------
INSERT INTO `mould` VALUES ('1', '192.168.0.105', '6b95cb3a-cb4e-11e6-952f-507b9db02f92');
INSERT INTO `mould` VALUES ('2', '192.168.0.18', '6b95cb3a-cb4e-11e6-952f-507b9db02f92');
INSERT INTO `mould` VALUES ('3', '192.168.1.169', '6b95cb3a-cb4e-11e6-952f-507b9db02f92');
INSERT INTO `mould` VALUES ('4', '127.0.0.1', '6b95cb3a-cb4e-11e6-952f-507b9db02f92');

-- ----------------------------
-- Table structure for `switch_status`
-- ----------------------------
DROP TABLE IF EXISTS `switch_status`;
CREATE TABLE `switch_status` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(3) DEFAULT NULL,
  `type_desc` varchar(30) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `status_desc` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of switch_status
-- ----------------------------
INSERT INTO `switch_status` VALUES ('1', '1', null, '1', null);
