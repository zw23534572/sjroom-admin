SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  DBTableInfo structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  id1                   bigint(20) not null auto_increment,
  name                 varchar(20) default NULL comment '用户姓名',
  ext_name             varchar(20) default NULL comment '用户姓名_扩展',
  status               int(4) default 0 comment '状态：0:创建中 1：创建成功， 2:创建失败',
  age                  int(4) default NULL comment '用户性别',
  primary key (id1)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user`(id1,name,ext_name,status,age)
VALUES
('1', 'zhouwei','用户姓名_扩展', 0,'3'),
('2', 'shengbin', NULL ,1,'2'),
('100', 'zhouwei','用户姓名_扩展', 0,'3'),
('200', 'shengbin', NULL ,1,'2');
COMMIT;
