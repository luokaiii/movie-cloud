> org.springframework.security.oauth2.core.OAuth2AuthenticationException: [authorization_request_not_found] 

原因：Authorization Request存储在Session中，默认情况下 Session 由 Cookie 管理。而我们在认证服务器上登录之后，第一个Cookie被设置为 localhost:9000 为存储授权请求的Session数据，当登录localhost:9003 时，会话被设置为另一个Cookie了。
尝试解决方法：
    使用SSO单点登录的方式，使多个客户端共享一个Cookie