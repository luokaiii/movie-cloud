package cn.luokaiii.user.config;

import cn.luokaiii.user.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity 安全配置，该配置会在ResourceServer之前执行
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http
//                .csrf().disable() // 关闭csrf
//                .formLogin().permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**","/login/**","/logout").permitAll()
//                .anyRequest().authenticated() // 拦截所有请求
//        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring() // 过滤Swagger等静态资源
                .antMatchers("/user/register", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/swagger-ui.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 将 AuthenticationManager 暴露出去
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 验证密码加密方式
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
