package cn.luokaiii.api.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/favicon.ico", "/webjars/**", "/images/**",
                        "/hystrix.stream/**", "/info", "/error", "/health", "/env", "/metrics", "/trace", "/dump",
                        "/actuator", "/actuator/**",
                        "/jolokia", "/configprops", "/activiti", "/logfile", "/refresh", "/flyway", "/liquibase", "/loggers");
    }
}
