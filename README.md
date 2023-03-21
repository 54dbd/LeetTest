<h1 align="center">Welcome to LeetTest 👋</h1>

![](https://s3.bmp.ovh/imgs/2022/07/13/ae47e07acbea20b7.png)
> 2021-2022 学年夏季学期《计算机应用大型作业》 --->>> **LeetTest (考研刷题网)**

本项目为前后端分离项目，前端采用Vue框架，UI采用element-UI,后端采用Springboot整合SSM，并采用Mybatis Plus简化CRUD操作。数据部分通过爬取获得。

## 作者

👤 **刘元**

👤 **朱若时**


## 🚀 项目启动

1. 前端启动
   - 自动下载相应的依赖 `npm install `
   - 启动前端项目，默认端口8080 `npm run serve`
2. 数据库部分
   - 执行项目路径下`/CSKY_db/CSKY.sql`脚本文件
   - 修改项目路径下`/CSKY_server/api/main/resources/application.yml`中的数据库配置

3. 后端启动
   - 使用IDE自动导入相应的Jar包
   - 启动后端项目，默认端口号80，可以在项目路径下`/CSKY_server/api/main/resources/application.yml`修改配置

# 整体效果

## 首页

作为展示页面，展示了招生公告、研招新闻、报考指南、研招答疑、考研大纲等文章简略信息。

## 用户注册登陆界面

注册时提供新用户邮箱，接收邮件之中的验证码，完成注册。

## 34所高校

提供了34所985高校的简介，支持关键词搜索，并可以根据每一个学校的简介，进入查看每一个学校的详情信息

1. 招考信息
2. 近三年分数线信息
3. 师资介绍

## 考研分数线

展示了34所985高校计算机相关专业近三年(21、20、19)的考研复试分数线，并且支持分数排序、分页展示等功能


## 帖子发表

支持Markdown格式的编写，在文章之中插入图片等，并且为文章添加相应的标签。文章发表后,文章中使用到的本地图片自动上传至七牛云图床。

## 文章评论

支持评论文章、回复评论、文章点赞、评论点赞、浏览量记录等功能

## 技术选型

#### 后端

| 技术        | 说明             |
| ----------- | ---------------- |
| SpringBoot  | 容器+MVC框架     |
| MyBatis     | ORM框架          |
| MyBatisPlus | 数据层代码生成   |
| Nginx       | 静态资源服务器   |
| Druid       | 数据库连接池     |
| JWT         | JWT登录支持      |
| Lombok      | 简化对象封装工具 |

#### 前端

| 技术       | 说明             |
| ---------- | ---------------- |
| Vue        | 前端框架         |
| Vue-router | 路由框架         |
| Vuex       | 全局状态管理框架 |
| Element    | 前端UI框架       |
| Axios      | 前端HTTP框架     |
| nprogress  | 进度条控件       |

#### 爬虫

| 技术  | 说明              |
| ----- | ----------------- |
| jsoup | 解析、提取HtmlTML |

## 目录结构

- 前端部分：

```
|-- CSKY_Client
    |-- src
        |-- App.vue -- 主页面
        |-- main.js -- 整体配置
        |-- api -- 接口配置
        |-- assets -- 静态资源
        |-- components -- 普通组件
        |-- pages -- 路由组件
        |-- routers -- 路由配置
        |-- store -- vuex配置
        |-- utils -- 工具包
```

- 后端部分：

```
|-- CSKY_Server
    |-- api -- controller层以及SpringBoot配置
    |-- beans -- 数据库表中映射的JavaBean以及返回的Vo对象
    |-- common -- 工具类及通用代码
    |-- mapper -- DAO层
    |-- service -- Service层
```

- 爬虫部分

```
|-- CSKY_Crawler
    |-- classes
    |	|-- Major				存储专业信息
    |	|-- School				存储学校信息
    |	|-- Teacher				存储教师信息
    |-- crawlers
    |	|-- getSchoolID			 获取学校ID（与网页URL对应）和学校名称
    |	|-- getCSInfo			 爬取公众号上学校的分数线（国家线）
    |	|-- ArticleOperating		  获取学校官方文章，保存URL
    |	|-- getTeacherInfo		  获取各个学校教师对应的网页URL
    |	|-- getTeacherDetail		  获取教师网页中教师的详细信息
    |-- ChangeInfo				 根据后期需求变化，对已有信息进行补充
```






