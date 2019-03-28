package cn.luokaiii.user.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(6)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 过滤静态资源、Actuator的健康检查路径、登录查询等公开接口
        web.ignoring().antMatchers(
                "/css/**", "/js/**", "/favicon.ico", "/webjars/**", "/images/**",
                "/hystrix.stream/**", "/info", "/error", "/health", "/actuator", "/actuator/**", "/env", "/metrics", "/trace", "/dump",
                "/jolokia", "/configprops", "/activiti", "/logfile", "/refresh", "/flyway", "/liquibase", "/loggers",
                "/user/name/**", "/user/phone/**", "/role/user/**", "/menu/user/*", "/client/all");
    }
}
