## 简介
本项目是基于我的开源common组件sjroom-boot.

所以用直接请先按步骤安装。

demo 网址
http://sjroom-admin.sjroom.cn/login.html

### 第一步
目前项目还未发布到中央仓库，所以需要用的话请下载代码maven install到本地仓库后在使用
以后在自己的本项目中添加
```bash
git clone https://github.com/zw23534572/sjroom-boot.git
```
### 第二步
```bash
mvn install
```
### 在项目的pom.xml添加
```xml
 <parent>
      <groupId>com.github.sjroom</groupId>
        <artifactId>sjroom-boot-pom</artifactId>
        <version>1.0.0-SNAPSHOT</version>
 </parent>
```


可以启动了。 

启动后的账号和密码  root 123456