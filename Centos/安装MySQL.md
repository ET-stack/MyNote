```
shell> wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
复制代码
```

##### 安装MySQL源

```
shell> sudo rpm -Uvh platform-and-version-specific-package-name.rpm
```

例如CentOS7当前最新MySQL源安装：

```
shell> sudo rpm -Uvh mysql80-community-release-el7-3.noarch.rpm
```

##### 检查是否安装成功

执行成功后会在`/etc/yum.repos.d/`目录下生成两个repo文件`mysql-community.repo`及 `mysql-community-source.repo`

并且通过`yum repolist`可以看到mysql相关资源

```
shell> yum repolist enabled | grep "mysql.*-community.*"
!mysql-connectors-community/x86_64 MySQL Connectors Community                108
!mysql-tools-community/x86_64      MySQL Tools Community                      90
!mysql80-community/x86_64          MySQL 8.0 Community Server                113
```

### 5、修改密码

##### 初始密码

MySQL第一次启动后会创建超级管理员账号`root@localhost`，初始密码存储在日志文件中：

```
shell> sudo grep 'temporary password' /var/log/mysqld.log
复制代码
```

##### 修改默认密码

```
shell> mysql -uroot -p
复制代码
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
ERROR 1819 (HY000): Your password does not satisfy the current policy requirements
```

设置远程访问

```
use mysql
update` `user` `set` `host = ``'%'` `where` `user` `= ``'root'``;
```