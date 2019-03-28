# User-Data-Service 用户数据服务

- user-data-api 实体、抽象API
- user-data-client 用户数据中公开的接口，需要使用的模块，通过依赖该模块，使用Feign来消费 provider中的具体实现，以及提供熔断降级服务
- user-data-provider 接口的具体实现






## QA

- 问题一：com.netflix.client.ClientException: load balancer does not have available server for client consul : xxxxxxx

    1. 场景：`auth-center` 项目无法使用 Feign 消费 `user-data` 中的接口服务
    2. 原因一：服务提供方没有引入依赖：spring-boot-starter-actuator
    3. 原因二：consul 中的健康检查没有通过，在我的项目中，将 "/actuator","/actuator/**" 加入 WebSecurityConfig 的忽略中即可
    
- 问题二：consul的安全检查未通过
    1. 原因一：/actuator/** 下的所有请求被 Security 拦截了，忽略即可
    
- 问题三：Provider中的接口，无法接收到参数
    1. 原因一：不要使用List做参数接收
    2. 原因二：Provider的接口上也需要加上 @PathVariable 或者 @RequestParam

