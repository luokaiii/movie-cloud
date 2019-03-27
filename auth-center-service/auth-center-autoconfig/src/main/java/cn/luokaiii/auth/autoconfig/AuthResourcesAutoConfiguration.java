package cn.luokaiii.auth.autoconfig;

import cn.luokaiii.auth.api.token.JwtAccessToken;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class AuthResourcesAutoConfiguration implements JwtAccessTokenConverterConfigurer {

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new JwtAccessToken();
    }


    @Override
    public void configure(JwtAccessTokenConverter jwtAccessTokenConverter) {
        jwtAccessTokenConverter.setAccessTokenConverter(jwtAccessTokenConverter());
    }
}
