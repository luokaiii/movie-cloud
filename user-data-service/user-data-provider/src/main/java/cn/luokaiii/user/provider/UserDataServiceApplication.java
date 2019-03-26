package cn.luokaiii.user.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringCloudApplication
public class UserDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDataServiceApplication.class, args);
    }

}
