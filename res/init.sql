-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 193.112.125.239    Database: sjroom-admin
-- ------------------------------------------------------
-- Server version	5.6.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'系统管理','','',0,'fa fa-cog',60),(2,1,'用户列表','modules/sys/user.html',NULL,1,'fa fa-user',1),(3,1,'角色管理','modules/sys/role.html',NULL,1,'fa fa-user-secret',2),(4,1,'菜单管理','modules/sys/menu.html',NULL,1,'fa fa-th-list',3),(5,1,'SQL监控','druid/sql.html',NULL,1,'fa fa-bug',4),(6,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0),(7,2,'新增',NULL,'sys:user:save,sys:role:select',2,NULL,0),(8,2,'修改',NULL,'sys:user:update,sys:role:select',2,NULL,0),(9,2,'删除',NULL,'sys:user:delete',2,NULL,0),(10,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0),(11,3,'新增',NULL,'sys:role:save,sys:menu:list',2,NULL,0),(12,3,'修改',NULL,'sys:role:update,sys:menu:list',2,NULL,0),(13,3,'删除',NULL,'sys:role:delete',2,NULL,0),(14,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0),(15,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0),(16,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0),(17,4,'删除',NULL,'sys:menu:delete',2,NULL,0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','sdfsdf','root','2018-05-09 15:06:17'),(11,'业务','','root','2018-11-17 19:02:49'),(12,'业务总监','','vista','2018-11-17 19:03:08'),(13,'预审','','root','2018-11-17 19:03:44'),(14,'面审','','root','2018-11-17 19:03:59'),(15,'终审','','root','2018-11-17 19:04:09'),(16,'考察','','root','2018-11-17 19:04:15'),(17,'风控总监','','vista','2018-11-17 19:04:29'),(18,'贷后','','vista','2018-11-17 19:04:38'),(19,'客服','','root','2018-11-17 19:04:51'),(20,'财务','','vista','2018-11-17 19:04:59');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(20) DEFAULT NULL COMMENT '账号',
  `user_name` varchar(20) NOT NULL COMMENT '用户名称',
  `password` varchar(100) NOT NULL DEFAULT '9898410d7f5045bc673db80c1a49b74f088fd7440037d8ce25c7d272a505bce5' COMMENT '密码',
  `salt` varchar(32) DEFAULT 'salt' COMMENT '密码加盐',
  `weixin_id` varchar(50) DEFAULT NULL COMMENT '微信ID',
  `dingding_id` varchar(50) DEFAULT NULL COMMENT '钉钉ID',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(1000) DEFAULT NULL COMMENT '钉钉的头像',
  `position` varchar(100) DEFAULT NULL COMMENT '职位信息',
  `department` varchar(20) DEFAULT NULL COMMENT '部门信息，部分可以存在多个',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `yn` tinyint(4) DEFAULT '1' COMMENT '有效标识,1有效 0.无效',
  `status` tinyint(4) DEFAULT '1' COMMENT '0:禁用,1:正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'root','超级管理员','e10adc3949ba59abbe56e057f20f883e','salt',NULL,NULL,'12343403@qq.com','1897371671',NULL,NULL,'1','2019-01-05 14:08:46','root','2018-12-01 12:23:19',NULL,1,1),(16,'vike.liu','vike','e10adc3949ba59abbe56e057f20f883e','salt',NULL,NULL,'vike.liu@dazong.com',NULL,NULL,NULL,'产品中心','2018-08-16 02:24:46','root','2018-11-10 00:37:48',NULL,0,0),(17,'test','test','e10adc3949ba59abbe56e057f20f883e','salt',NULL,NULL,'123433403@qq.com','','','',NULL,'2018-12-31 14:25:17','test','2018-11-15 01:33:05',NULL,1,0),(18,'edwin.gu','test12323','e10adc3949ba59abbe56e057f20f883e','salt',NULL,NULL,'edwin.gu@dazong.com','1897371678x','','',NULL,'2018-08-22 03:21:01','root','2018-11-22 18:54:19',NULL,0,0),(19,'test1','test','e10adc3949ba59abbe56e057f20f883e','',NULL,NULL,'123433402@qq.com','1897371678x','','',NULL,'2018-11-13 21:58:05','root','2018-11-13 21:58:05',NULL,0,0),(20,'xiaoming','小明','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'xiaoming@126.com','','','',NULL,'2019-01-02 14:47:08','root','2018-11-30 03:38:02',NULL,1,1),(21,'xiaohong','小洪','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'xiaohong@126.com','1367654321x','','',NULL,'2018-11-24 08:16:35','root','2018-11-24 00:16:38',NULL,1,0),(22,'xiaozeng','小曾','e10adc3949ba59abbe56e057f20f883e','',NULL,NULL,'xiaozeng@126.com','','','',NULL,'2018-11-24 08:22:51','root','2018-11-24 00:22:54',NULL,1,0),(23,'xiaoyang','小杨','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'xiaoyang@126.com','','','',NULL,'2018-11-24 08:24:16','root','2018-11-24 00:24:19',NULL,0,0),(24,'xiaoli','小李','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'xiaoli@126.com',NULL,NULL,NULL,NULL,'2018-11-24 08:24:55','root','2018-11-24 00:24:58',NULL,0,0),(25,'xiaoluo','小罗','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'xiaoluo@126.com',NULL,NULL,NULL,NULL,'2018-11-24 09:11:42','root','2018-11-24 01:11:45',NULL,0,0),(26,'vista','路建军','e10adc3949ba59abbe56e057f20f883e','',NULL,NULL,'65961566@qq.com','1379856965x','','',NULL,'2019-01-04 07:23:30','root','2018-12-05 10:06:24',NULL,1,1),(27,'lvchen','吕晨','bece0b17c9619326f27e88f998283a6c','',NULL,NULL,'659615661@qq.com','1363961950x','','',NULL,'2019-01-01 11:43:58','root','2018-12-05 10:08:06',NULL,1,1),(28,'yangchao','杨超','bece0b17c9619326f27e88f998283a6c','',NULL,NULL,'659615662@qq.com','1892650581x','','',NULL,'2018-12-20 02:04:17','root','2018-12-05 10:09:24',NULL,1,1),(29,'maohua','谢茂华','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'659615663@qq.com','1307692960x','','',NULL,'2018-12-25 14:51:16','root','2018-12-05 10:10:32',NULL,1,1),(30,'guibin','崔贵宾','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'659615664@qq.com','1351089050x','','',NULL,'2018-12-25 15:15:15','root','2018-12-05 10:23:02',NULL,1,1),(31,'qiuhua','谢秋华','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'659615665@qq.com','1368240280x','','',NULL,'2018-12-25 15:20:01','root','2018-12-05 10:24:28',NULL,1,1),(32,'guo','郭振邦','a4fa061608caf740f76da9d5fce47d10','',NULL,NULL,'guozhenbang888@163.com','1382366991x','','',NULL,'2018-12-06 09:57:43','root','2018-12-06 08:26:19',NULL,1,1),(33,'caiwu','财务','21218cca77804d2ba1922c33e0151105','',NULL,NULL,'123@qq.com','123','','',NULL,'2018-12-25 15:13:54','root','2018-12-07 07:38:59',NULL,1,1),(34,'yewuzj','业务总监','202cb962ac59075b964b07152d234b70','',NULL,NULL,'1234@qq.com','123',NULL,NULL,NULL,'2018-12-07 07:41:55','vista','2018-12-07 07:39:40',NULL,1,1),(35,'fengkong','风控总监','202cb962ac59075b964b07152d234b70','',NULL,NULL,'12345@qq.com','123',NULL,NULL,NULL,'2018-12-07 07:42:42','vista','2018-12-07 07:40:51',NULL,1,1),(36,'jiaojiao','黄慧姣','22bd92bc577519a68e8c08a3c1dc8f10','',NULL,NULL,'12@qq.com','66771',NULL,NULL,NULL,'2018-12-20 09:21:39','vista','2018-12-17 06:07:47',NULL,1,1),(37,'yunkai','吴云楷','80008d373fb994980c605cc292501f3d','',NULL,NULL,'aa@qq.com','1382379860x','','',NULL,'2018-12-19 09:51:59','root','2018-12-19 09:51:44',NULL,1,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,1),(14,24,16),(15,25,19),(38,34,12),(39,35,17),(49,36,11),(50,36,13),(74,22,14),(75,22,20),(78,23,15),(79,33,20),(86,17,11),(87,17,1),(88,20,11),(89,20,13),(90,32,17),(91,32,18),(92,31,11),(93,31,13),(94,30,11),(95,30,18),(96,30,13),(97,29,11),(98,29,13),(99,28,18),(100,28,11),(101,28,16),(102,37,11),(103,26,1),(104,26,12),(105,26,14),(106,26,17),(107,27,19),(108,27,13),(109,27,11),(110,21,13),(111,19,1),(112,18,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_token`
--

DROP TABLE IF EXISTS `sys_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `token` varchar(100) NOT NULL COMMENT 'token',
  `session_id` varchar(100) DEFAULT NULL COMMENT 'JSESSIONID',
  `expire_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新人',
  `yn` tinyint(4) DEFAULT '1' COMMENT '有效标识 1:有效 0:无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';
/*!40101 SET character_set_client = @saved_cs_client */;
