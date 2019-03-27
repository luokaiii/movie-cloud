package cn.luokaiii.auth.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringCloudApplication
@EnableAuthorizationServer
@EnableFeignClients("cn.luokaiii.user.client")
public class AuthCenterProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthCenterProviderApplication.class, args);
    }
}
