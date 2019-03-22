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
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器配置
 */
@Slf4j
@Configuration
@EnableOAuth2Client
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

    /**
     * 配置授权服务器的安全
     *
     * @param security AuthorizationServerSecurityConfigurer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
//        security
//                .allowFormAuthenticationForClients() // 允许对客户端进行表单身份验证
//                .checkTokenAccess("isAuthenticated")
//                .tokenKeyAccess("permitAll()");
        log.info("AuthorizationServerSecurityConfigurer is complete!");
    }

    /**
     * 配置 ClientDetailsService，至少需要配置一个 Client，否则服务器将不会启动
     * 在授权方式中，一般会使用两种授权方式：password 和 client_credentials
     * - password 模式，使用认证服务器中的用户，在登录时带上 用户账号和客户端账号，请求到的 TokenAccess 中包含认证服务器的用户信息，而不是客户端自己的 authorization
     * http://localhost:9000/oauth/token?username=user&password=1234&grant_type=password&client_id=movie&client_secret=123456&scope=all
     * - client_credentials 客户端模式，没有用户的概念，直接使用客户端账号请求 TokenAccess，这时可以使用自己的用户信息，来实现自定义的 authorization
     * - authorization_code 授权码模式，使用的是回调地址，一般的第三方登录会使用这种方式，较为复杂
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // todo 使用DB存储客户端的配置，或者通过配置文件存储
        clients.inMemory()
                // Movie-Service
                .withClient("movie-service")
                .secret(passwordEncoder.encode("movie-service"))
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .redirectUris("http://localhost:9001/login/oauth2/code/callback")
                .autoApprove("all")
                .scopes("all")
                .and()
                // Comment-Service
                .withClient("comment-service")
                .secret(passwordEncoder.encode("comment-service"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:9003/login/oauth2/code/callback")
                .scopes("read", "write", "all")
                .autoApprove("all")
                .and()
                // User-Service
                .withClient("user-service")
                .secret(passwordEncoder.encode("user-service"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .and()
                // Record-Service
                .withClient("record-service")
                .secret(passwordEncoder.encode("record-service"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:9005/login/oauth2/code/callback", "http://localhost:9005/login")
                .scopes("all")
        ;
    }

    /**
     * 配置 Authorization Server EndPoints 的一些非安全特性
     * 比如 token 存储、token自定义、授权类型等
     * 默认情况下，不需要做任何事情，但是如果需要使用密码授权模式，则需要提供一个 AuthenticationManager
     *
     * @param endpoints Authorization Server EndPoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore());
//                .userDetailsService(userDetailsService)
//                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore(); // 使用Memory存储Token，如果项目重新登录，就会需要重新获取授权
        return new RedisTokenStore(redisConnectionFactory);
    }
}
