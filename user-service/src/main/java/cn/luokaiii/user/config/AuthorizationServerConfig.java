package cn.luokaiii.user.config;

import cn.luokaiii.user.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器配置
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final MyUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public AuthorizationServerConfig(PasswordEncoder passwordEncoder,
                                     MyUserDetailsService userDetailsService,
                                     AuthenticationManager authenticationManager,
                                     RedisConnectionFactory redisConnectionFactory) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .allowFormAuthenticationForClients() // 允许对客户端进行表单身份验证
                .checkTokenAccess("isAuthenticated")
                .tokenKeyAccess("permitAll()");
        log.info("AuthorizationServerSecurityConfigurer is complete!");
    }

    /**
     * 配置加载服务
     * 在授权方式中，一般会使用两种授权方式：password 和 client_credentials
     * password 模式，使用认证服务器中的用户，在登录时带上 用户账号和客户端账号，请求到的 TokenAccess 中包含认证服务器的用户信息，而不是客户端自己的 authorization
     * http://localhost:9000/oauth/token?username=user&password=1234&grant_type=password&client_id=movie&client_secret=123456&scope=all
     * client_credentials 客户端模式，没有用户的概念，直接使用客户端账号请求 TokenAccess，这时可以使用自己的用户信息，来实现自定义的 authorization
     * authorization_code 授权码模式，使用的是回调地址，一般的第三方登录会使用这种方式，较为复杂
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("movie-service")
                // todo 这里的 secret 有时需要加密，有时不需要
                .secret(passwordEncoder.encode("movie-service"))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore(); // 使用Memory存储Token，如果项目重新登录，就会需要重新获取授权
        return new RedisTokenStore(redisConnectionFactory);
    }
}
