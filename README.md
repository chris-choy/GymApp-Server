<div align="center">
 <img src="https://user-images.githubusercontent.com/23289235/150794034-37a77fec-0a5d-4289-8d14-e844bccf465e.png" height = "150" alt="图片名称" />
</div>

[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

## 1.背景介绍
此项目基于Spring Boot和MySQL创建，作为iOS的GymAPP项目后端应用，提供查询数据存储数据、以及基本的用户安全保障。

## 2.特点
1. 客户端与服务端通过JWT认证授权请求。
2. 集成了Swagger，可方便查看API接口的使用说明。

## 3.测试环境
1. IDE: IntelliJ IDEA 2021.3
2. 数据库: MySQL
3. JRE：corretto-11 "11.0.12"
4. Spring Boot: 2.4.5

## 4.运行环境配置

### 4.1 配置数据库路径
文件路径

    ~/src/main/resources/application.properties

在上述文件中设置MySQL数据库的相关内容

1. `ip地址`
2.  `端口号`
3. `数据库名称`
4. `用户名`
5. `密码`

```
spring.datasource.url = jdbc:mysql://[ip地址]:[端口号]/[数据库名称]?useSSL=false&characterEncoding=utf8
spring.datasource.username = [用户名]
spring.datasource.password = [密码]
```

### 4.2 数据库设置
    ~/DataBase/initial.sql
根据上述路径中的initial.sql文件初始化数据库，其中包括
1. 创建数据库
2. 创建表。
3. 设置禁用全局自动提交。
4. 插入事例数据到对应的表。

## 5.使用方法
### 5.1 运行
配置好运行环境之后，直接运行即可访问。
### 5.2 查看Api文档（Swagger）
根据运行程序的主机ip地址访问，默认为loacalhost，端口后8080。

    http://localhost:8080/swagger-ui/

## 7.问题反馈
1. 欢迎提交issue。
2. Email: ct.choi@outlook.com
