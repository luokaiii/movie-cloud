package cn.luokaiii.record;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@SpringBootApplication
public class RecordServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordServiceApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity hello(HttpSession session) {
        return ResponseEntity.ok("Hello " + session.getId());
    }

    @GetMapping("/user")
    public ResponseEntity user(Principal user) {
        return ResponseEntity.ok(user);
    }

}
