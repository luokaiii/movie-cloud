package cn.luokaiii.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableOAuth2Client
@RestController
@SpringBootApplication
public class MovieServiceApplication {

//    @Qualifier("oauth2ClientContext")
//    @Autowired
//    private OAuth2ClientContext oAuth2ClientContext;
//
//    @Bean
//    public OAuth2RestTemplate restTemplate(){
//        return new OAuth2RestTemplate(new AuthorizationCodeResourceDetails(),oAuth2ClientContext);
//    }

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }

    // todo 这里有一个session一致性的问题
    @GetMapping("/")
    public String hello() {
        return String.format("<h1>Hello World!</h1>");
    }

}
