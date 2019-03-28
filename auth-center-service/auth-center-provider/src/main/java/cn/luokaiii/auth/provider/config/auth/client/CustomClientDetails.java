package cn.luokaiii.auth.provider.config.auth.client;

import cn.luokaiii.user.api.model.MovieClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class CustomClientDetails implements ClientDetails {

    private MovieClientDetails clientDetails;

    public CustomClientDetails(MovieClientDetails clientDetails) {
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
