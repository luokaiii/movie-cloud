package cn.luokaiii.user.api.model;

import cn.luokaiii.common.model.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings(value = "")
/**
 * 系统客户端，可以通过 OAuth2 认证的客户端
 *  - 客户端的注册，位于 {@link cn.luokaiii.auth.provider.config.auth.AuthorizationServerConfig#configure(clients)}
 *  - 具体可参考 {@link org.springframework.security.oauth2.provider.ClientDetails}
 */
@Document(collection = "system_client")
public class MovieClientDetails extends BaseEntity implements Serializable {

    private String name;

    private String clientId; // 客户端ID

    private String clientSecret; // 客户端密码

    private Set<String> resourceIds; // 客户端拥有的资源

    private Set<String> scope;

    private Set<String> authorizedGrantTypes; // 客户端授权方式

    private String webServerRedirectUri; // 客户端回调地址

    private Collection<String> authorities;

    private Integer accessTokenValidity; // access_token的有效期

    private Integer refreshTokenValidity; // refresh_token的有效期

    private Map<String, Object> additionalInformation; // 附加信息

    private Boolean autoapprove;

    @Id
    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    public Boolean getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(Boolean autoapprove) {
        this.autoapprove = autoapprove;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }


}
