package cn.luokaiii.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@SpringBootApplication
public class MovieServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }

    // todo 这里有一个session一致性的问题
    @GetMapping("/")
    public String hello(HttpSession session) {
        return String.format("<h1>Hello World! %s</h1>", session.getId());
    }

}
