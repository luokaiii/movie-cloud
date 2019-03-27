
- 问题一：`auth-center` 项目无法使用 Feign 消费 `user-data` 中的接口服务

1. 异常：com.netflix.client.ClientException: load balancer does not have available server for client consul : xxxxxxx
    1. 原因一：服务提供方没有引入依赖：spring-boot-starter-actuator
    2. 原因二：consul 中的健康检查没有通过，在我的项目中，将 "/actuator","/actuator/**" 加入 WebSecurityConfig 的忽略中即可
    3. 原因三：