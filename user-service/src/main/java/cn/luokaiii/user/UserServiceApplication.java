package cn.luokaiii.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@SpringBootApplication
public class UserServiceApplication {

    @Autowired
    TokenStore tokenStore;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @GetMapping("/")
    public String hello(HttpSession session) {
        tokenStore.findTokensByClientId("user-service");
        return String.format("<h1>Hello World! %s</h1>", session.getId());
    }
}
