# Auth-Center 认证授权中心

## 使用MongoDB作为客户端配置源

一般情况下，在认证服务器配置客户端，给予了两种实现方式
- 一种是 `InMemoryClientDetailsService` 使用内存存储客户端的配置
- 另一种是 `JdbcClientDetailsService`  使用JDBC数据源 读取客户端的配置

### 使用内存配置

一般的教程中，都是给的使用内存的方式，直接注册客户端。
先展示，然后再重写.

```java
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
     clients
             .inMemory()
             .withClient("test")
             .secret("test")
             .scopes("user")
             .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit", "client_credentials")
             .accessTokenValiditySeconds(43200)
             .autoApprove(false);
    }
    
    // ......
}
```

> 很简单，只需要一个能够重写 ClientDetailsService 的实现，以及一个自定义的 ClientDetails 对象即可。

### 客户端对象 - CustomClientDetails 

自定义的客户端配置对象.
具体的参数，可以参考 `org.springframework.security.oauth2.provider.ClientDetails` 对象，大致包含客户端ID等信息.

```java
@Document(collection = "system_client")
@Getter
@Setter // 这个是lombok的缩略写法
public class CustomClientDetails {

    @Id
    private String id;

    private String name;

    private String clientId; // 客户端ID

    private String clientSecret; // 客户端密码

    private Set<String> resourceIds; // 客户端拥有的资源

    private Set<String> scope;

    private Set<String> authorizedGrantTypes; // 客户端授权方式

    private Set<String> webServerRedirectUri; // 客户端回调地址

    private Collection<String> authorities;

    private Integer accessTokenValidity; // access_token的有效期

    private Integer refreshTokenValidity; // refresh_token的有效期

    private Map<String, Object> additionalInformation; // 附加信息

    private Boolean autoapprove;
}
```

### 服务端对象 - AuthClientDetails

因为就业务而言，`CustomClientDetails` 存储在Mongo上的话，与认证服务器一般不处于同一个服务中。
那么业务服务，就没有依赖 OAuth 包，也就无法实现 `ClientDetails`，这里我们做一个封装，将 `CustomClientDetails` 转换成 `ClientDetails`。

```java
public class AuthClientDetails implements ClientDetails {

    private CustomClientDetails clientDetails;

    public AuthClientDetails(CustomClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    @Override
    public String getClientId() {
        return clientDetails.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clientDetails.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientDetails.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return clientDetails.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return clientDetails.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return clientDetails.getWebServerRedirectUri();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Collection<String> collection = clientDetails.getAuthorities();
        for (String s : collection) {
            authorities.add(new SimpleGrantedAuthority(s));
        }
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDetails.getAccessTokenValidity();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetails.getRefreshTokenValidity();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return clientDetails.getAutoapprove();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return clientDetails.getAdditionalInformation();
    }
}
``` 

### 实现 ClientDetailsService

```java
@Component
public class MongoClientDetailsService implements ClientDetailsService {

    private Map<String,CustomClientDetails> clientDetailsMap;

    public MongoClientDetailsService(MongoTemplate mongoTemplate) {
        // 在构造函数执行时，就读取客户端的配置
        clientDetailsMap = mongoTemplate.findAll(CustomClientDetails.class)
                .stream().collect(Collectors.toMap(CustomClientDetails::getClientId,val -> val));
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        CustomClientDetails details = clientDetailsMap.get(clientId);
        if (details == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        // 通过 AuthClientDetails 将 CustomClientDetails 转成 ClientDetails
        return new AuthClientDetails(details);
    }
}
```

### 使用Mongo的配置覆盖Memory

```java
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Bean
    public MongoClientDetailsService mongoClientDetailsService(){
        return new MongoClientDetailsService(mongoTemplate);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(mongoClientDetailsService());
    }
    
    // ......
}
```