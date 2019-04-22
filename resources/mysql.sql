/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : sun_jwd

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-22 11:50:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sun_address_book`
-- ----------------------------
DROP TABLE IF EXISTS `sun_address_book`;
CREATE TABLE `sun_address_book` (
  `table_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cus_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_address_book
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_attest_type`
-- ----------------------------
DROP TABLE IF EXISTS `sun_attest_type`;
CREATE TABLE `sun_attest_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `type_code` varchar(64) DEFAULT NULL COMMENT '类型编码',
  `type_name` varchar(255) NOT NULL COMMENT '类型名称',
  `display_order` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_attest_type
-- ----------------------------
INSERT INTO `sun_attest_type` VALUES ('1', 'SAT001', '基本信息', '1');
INSERT INTO `sun_attest_type` VALUES ('2', 'SAT002', '支付宝', '2');
INSERT INTO `sun_attest_type` VALUES ('3', 'SAT003', '淘宝', '3');
INSERT INTO `sun_attest_type` VALUES ('4', 'SAT004', '运营商', '4');
INSERT INTO `sun_attest_type` VALUES ('5', 'SAT005', '征信', '5');
INSERT INTO `sun_attest_type` VALUES ('6', 'SAT006', '社保', '6');
INSERT INTO `sun_attest_type` VALUES ('7', 'SAT007', '公积金', '7');
INSERT INTO `sun_attest_type` VALUES ('8', 'SAT008', '通讯录', '8');
INSERT INTO `sun_attest_type` VALUES ('9', 'SAT009', '补充信息', '9');

-- ----------------------------
-- Table structure for `sun_com_user`
-- ----------------------------
DROP TABLE IF EXISTS `sun_com_user`;
CREATE TABLE `sun_com_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键，员工ID',
  `emp_id` varchar(255) NOT NULL COMMENT '员工编号',
  `name` varchar(255) NOT NULL COMMENT '员工姓名',
  `password` varchar(255) NOT NULL COMMENT '账号密码',
  `gender` varchar(64) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `phone` varchar(255) NOT NULL COMMENT '手机号码',
  `telephone` varchar(255) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ号码',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信号码',
  `department1` int(10) unsigned DEFAULT NULL COMMENT '公司组织：一级部门',
  `department2` int(10) unsigned DEFAULT NULL COMMENT '公司组织：二级部门',
  `department3` int(10) unsigned DEFAULT NULL COMMENT '公司组织：三级部门',
  `status` varchar(64) NOT NULL DEFAULT 'Y' COMMENT '用户状态（启用Y/停用N）',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `emp_id` (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_com_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_cus_user`
-- ----------------------------
DROP TABLE IF EXISTS `sun_cus_user`;
CREATE TABLE `sun_cus_user` (
  `cus_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `sys_id` varchar(255) DEFAULT NULL COMMENT '系统中的编号',
  `name` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '账号密码',
  `gender` varchar(64) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `identity_card` varchar(255) DEFAULT NULL COMMENT '身份证号码',
  `mobile_number` varchar(255) NOT NULL COMMENT '手机号码',
  `tele_number` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `qq_number` varchar(255) DEFAULT NULL COMMENT 'qq号码',
  `wechat_number` varchar(255) DEFAULT NULL COMMENT '微信号码',
  `create_date` date DEFAULT NULL COMMENT '注册时间',
  `manager_id` int(11) DEFAULT NULL COMMENT '客户经理（ID）',
  `comment` varchar(1024) DEFAULT NULL COMMENT '备注',
  `sesame_seed` int(11) DEFAULT NULL COMMENT '芝麻分',
  `place` varchar(255) DEFAULT NULL COMMENT '居住地',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `photo_right` mediumtext COMMENT '身份证正面照url',
  `photo_left` mediumtext COMMENT '身份证反面照url',
  `photo` mediumtext COMMENT '手持身份证照url',
  `status` varchar(64) DEFAULT 'Y' COMMENT '客户状态，允许申请客户（Y/N）',
  `pay_count` int(11) DEFAULT '0' COMMENT '结清次数',
  `self_photo` mediumtext,
  PRIMARY KEY (`cus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_cus_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_cus_user_add`
-- ----------------------------
DROP TABLE IF EXISTS `sun_cus_user_add`;
CREATE TABLE `sun_cus_user_add` (
  `cus_add_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `cus_id` int(11) NOT NULL COMMENT '用户表ID,外键',
  `salary` decimal(10,2) DEFAULT NULL COMMENT '月收入',
  `debt` decimal(10,2) DEFAULT NULL COMMENT '当前债务',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `company_post` varchar(255) DEFAULT NULL COMMENT '职位',
  `company_tele` varchar(255) DEFAULT NULL COMMENT '公司电话',
  `company_place` varchar(255) DEFAULT NULL COMMENT '公司所在地',
  `company_address` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `contact1_name` varchar(255) DEFAULT NULL COMMENT '紧急联系人1姓名',
  `contact1_mobile` varchar(255) DEFAULT NULL COMMENT '紧急联系人1手机号码',
  `contact2_name` varchar(255) DEFAULT NULL COMMENT '紧急联系人2姓名',
  `contact2_mobile` varchar(255) DEFAULT NULL COMMENT '紧急联系人2手机号码',
  `reserve_mobile` varchar(255) DEFAULT NULL COMMENT '备用手机号',
  `wife_name` varchar(255) DEFAULT NULL,
  `wife_tel` varchar(255) DEFAULT NULL,
  `friend_name` varchar(255) DEFAULT NULL,
  `friend_tel` varchar(255) DEFAULT NULL,
  `collegue_name` varchar(255) DEFAULT NULL,
  `collegue_tel` varchar(255) DEFAULT NULL,
  `asset` varchar(255) DEFAULT NULL,
  `insurance` varchar(255) DEFAULT NULL,
  `mobile_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cus_add_id`),
  UNIQUE KEY `unique_sun_cus_user_add` (`cus_id`),
  UNIQUE KEY `cus_id` (`cus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_cus_user_add
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_department`
-- ----------------------------
DROP TABLE IF EXISTS `sun_department`;
CREATE TABLE `sun_department` (
  `depa_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键，部门ID',
  `depa_code` varchar(255) DEFAULT NULL COMMENT '部门编码',
  `name` varchar(255) NOT NULL DEFAULT '部门名称未定义' COMMENT '部门名称',
  `grade` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '部门等级',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT '上级部门ID',
  `display_order` int(10) unsigned DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`depa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_department
-- ----------------------------
INSERT INTO `sun_department` VALUES ('1', 'XS', '销售部', '1', null, '1');
INSERT INTO `sun_department` VALUES ('2', 'SW', '业务部', '1', null, '3');
INSERT INTO `sun_department` VALUES ('3', 'CW', '财务部', '1', null, '2');
INSERT INTO `sun_department` VALUES ('4', 'XS_1', '销售1组', '2', '1', '1');
INSERT INTO `sun_department` VALUES ('5', 'XS_2', '销售2组', '2', '1', '2');

-- ----------------------------
-- Table structure for `sun_order`
-- ----------------------------
DROP TABLE IF EXISTS `sun_order`;
CREATE TABLE `sun_order` (
  `order_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `order_number` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `cus_id` int(11) NOT NULL COMMENT '客户信息表ID',
  `name` varchar(255) NOT NULL COMMENT '用户姓名',
  `mobile_number` varchar(255) NOT NULL COMMENT '手机号码',
  `identity_card` varchar(255) NOT NULL COMMENT '身份证号码',
  `gender` varchar(64) NOT NULL COMMENT '性别',
  `salary` decimal(10,2) DEFAULT NULL COMMENT '月收入',
  `apply_money` decimal(10,2) NOT NULL COMMENT '申请金额',
  `date_count` int(11) NOT NULL DEFAULT '1' COMMENT '申请天数',
  `pay_count` int(11) DEFAULT '0' COMMENT '当前结清次数（提交申请时）',
  `apply_date` date NOT NULL COMMENT '申请时间',
  `sesame_seed` int(11) DEFAULT NULL COMMENT '芝麻分',
  `approve_money` decimal(10,2) DEFAULT NULL COMMENT '审批金额',
  `approve_first_flag` varchar(64) NOT NULL DEFAULT 'S' COMMENT '初审标志（待审核-S/审核通过-Y/审核退回-N）',
  `approve_first_comment` varchar(1024) DEFAULT NULL COMMENT '初审备注',
  `approve_first_user` int(11) DEFAULT NULL COMMENT '初审人,公司用户ID',
  `approve_first_date` datetime DEFAULT NULL COMMENT '初审时间',
  `approve_final_flag` varchar(64) NOT NULL DEFAULT 'S' COMMENT '终审标志（待审核-S/审核通过-Y/审核退回-N）',
  `approve_final_comment` varchar(1024) DEFAULT NULL COMMENT '终审备注',
  `approve_final_user` int(11) DEFAULT NULL COMMENT '终审人，公司用户ID',
  `approve_final_date` datetime DEFAULT NULL COMMENT '终审时间',
  `grant_flag` varchar(64) NOT NULL DEFAULT 'S' COMMENT '放款标志',
  `grant_money` decimal(10,2) DEFAULT NULL COMMENT '放款金额',
  `grant_date` date DEFAULT NULL COMMENT '放款时间',
  `return_date` date DEFAULT NULL COMMENT '还款时间',
  `comment` varchar(1024) DEFAULT NULL COMMENT '放款备注',
  `action_user` int(11) DEFAULT NULL COMMENT '放款人，公司用户ID',
  `action_date` datetime DEFAULT NULL COMMENT '放款操作时间',
  `punish_money` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '累计惩罚金额',
  `pay_money` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '累计还款金额',
  `over_flag` varchar(64) DEFAULT NULL COMMENT '结清标志(结清-Y/未结清(逾期)-N)',
  `degree` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '还款次数',
  `remain_return_date` date DEFAULT NULL COMMENT '剩余还款时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_order
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_pay`
-- ----------------------------
DROP TABLE IF EXISTS `sun_pay`;
CREATE TABLE `sun_pay` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `pay_number` varchar(255) DEFAULT NULL COMMENT '支付单编号',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `order_number` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `punish_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '惩罚金额',
  `pay_money` decimal(10,2) NOT NULL COMMENT '还款金额',
  `pay_date` date NOT NULL COMMENT '还款时间',
  `over_flag` varchar(64) NOT NULL DEFAULT 'N' COMMENT '结清标志(结清-Y/未结清-N)',
  `remain_money` decimal(10,2) DEFAULT NULL COMMENT '剩余金额',
  `remain_return_date` date DEFAULT NULL COMMENT '剩余还款时间',
  `pay_count` int(11) NOT NULL COMMENT '订单还款次数',
  `repay_comment` varchar(1024) DEFAULT NULL COMMENT '备注',
  `action_user` int(11) NOT NULL COMMENT '操作人，公司用户ID',
  `action_date` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_pay
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_user_attest`
-- ----------------------------
DROP TABLE IF EXISTS `sun_user_attest`;
CREATE TABLE `sun_user_attest` (
  `attest_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `type_id` int(11) NOT NULL COMMENT '认证类型ID',
  `message_id` varchar(255) DEFAULT NULL COMMENT '认证数据信息ID,对应不同认证类型信息表的ID',
  `status_flag` varchar(255) DEFAULT 'N' COMMENT '认证信息获取结果（成功-Y/失败-N）',
  `message1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`attest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_user_attest
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_xinyan_credit`
-- ----------------------------
DROP TABLE IF EXISTS `sun_xinyan_credit`;
CREATE TABLE `sun_xinyan_credit` (
  `table_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cus_id` int(11) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `id_name` varchar(255) DEFAULT NULL,
  `id_no` varchar(255) DEFAULT NULL,
  `credit_type` varchar(255) NOT NULL,
  `data` mediumtext,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_xinyan_credit
-- ----------------------------

-- ----------------------------
-- Table structure for `sun_xinyan_trans`
-- ----------------------------
DROP TABLE IF EXISTS `sun_xinyan_trans`;
CREATE TABLE `sun_xinyan_trans` (
  `table_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `trans_id` varchar(255) DEFAULT NULL,
  `trans_date` varchar(255) DEFAULT NULL,
  `cus_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `success` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=374 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sun_xinyan_trans
-- ----------------------------