package cn.luokaiii.auth.autoconfig.utils;

import cn.luokaiii.auth.api.pojo.Constant;
import cn.luokaiii.user.api.model.MovieUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;

public class AccessTokenUtils {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenExtractor tokenExtractor;

    /**
     * 从token获取用户信息
     */
    public MovieUser getUserInfo() {
        return (MovieUser) getAccessToken().getAdditionalInformation().get(Constant.USER_INFO);
    }

    private OAuth2AccessToken getAccessToken() throws AccessDeniedException {
        OAuth2AccessToken token;
        // 抽取token
        Authentication a = tokenExtractor.extract(request);
        try {
            // 调用JwtAccessTokenConverter的extractAccessToken方法解析token
            token = tokenStore.readAccessToken((String) a.getPrincipal());
        } catch (Exception e) {
            throw new AccessDeniedException("AccessToken Not Found.");
        }
        return token;
    }
}
