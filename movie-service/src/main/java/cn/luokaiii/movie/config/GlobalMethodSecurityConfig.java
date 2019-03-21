package cn.luokaiii.movie.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableOAuth2Sso
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest()
                .authenticated();
    }
}
