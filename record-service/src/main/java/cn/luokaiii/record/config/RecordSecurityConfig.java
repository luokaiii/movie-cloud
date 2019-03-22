package cn.luokaiii.record.config;

import cn.luokaiii.record.support.MyUserInfoTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableOAuth2Sso
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RecordSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ResourceServerProperties p;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public ResourceServerTokenServices userInfoTokenServices() {
        return new MyUserInfoTokenServices(p.getUserInfoUri(), p.getClientId());
    }
}
