# Movie-Cloud 微服务架构

### Module 项目模块

- common-service 公共脚手架，包含如公共实体、异常类、抽象服务、工具类等
- auth-center-service 认证服务，采用 OAuth2 + Jwt 的认证授权方式
- user-data-service 用户信息服务，提供包含 User 相关的 CRUD
- api-gateway 网关服务，由 Zuul 进行代理及认证 Token 的转发

### 系统架构图

![系统架构图](https://github.com/luokaiii/movie-cloud/blob/master/image/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)