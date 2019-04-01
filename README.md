# Movie-Cloud 微服务架构

### Module 项目模块

- common-service 公共脚手架，包含如公共实体、异常类、抽象服务、工具类等
- auth-center-service 认证服务，采用 OAuth2 + Jwt 的认证授权方式
- user-data-service 用户信息服务，提供包含 User 相关的 CRUD
- api-gateway 网关服务，由 Zuul 进行代理及认证 Token 的转发

### framework 系统架构

![系统架构图](https://github.com/luokaiii/movie-cloud/blob/master/image/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

### Authority 权限系统架构

![权限架构](https://github.com/luokaiii/movie-cloud/blob/master/image/Movie-%E8%A7%92%E8%89%B2%E7%B3%BB%E7%BB%9F.jpg)

[![LICENSE](https://img.shields.io/badge/license-NPL%20(The%20996%20Prohibited%20License)-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)

