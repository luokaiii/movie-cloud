# Movie-Cloud 微服务架构

### Module 项目模块

- common-service 公共脚手架，包含如公共实体、异常类、抽象服务、工具类等
- auth-center-service 认证服务，采用 OAuth2 + Jwt 的认证授权方式
- user-data-service 用户信息服务，提供包含 User 相关的 CRUD
- api-gateway 网关服务，由 Zuul 进行代理及认证 Token 的转发