package cn.luokaiii.auth.autoconfig.utils;

import cn.luokaiii.auth.api.pojo.Constant;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.api.model.MovieUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AccessTokenUtils {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private RedisTemplate<String, MovieRole> roleTemplate;

    @Autowired
    private RedisTemplate<String, ModuleResources> resourcesTemplate;

    /**
     * 从token获取用户信息
     */
    public MovieUser getUserInfo() {
        return (MovieUser) getAccessToken().getAdditionalInformation().get(Constant.USER_INFO);
    }

    public List<MovieRole> getRoleInfo() {
        String userId = getUserInfo().getId();
        long size = roleTemplate.opsForList().size(userId);
        return roleTemplate.opsForList().range(userId, 0, size);
    }

    public List<ModuleResources> getMenuInfo() {
        String key = getUserInfo().getId() + "-menu";
        long size = resourcesTemplate.opsForList().size(key);
        return resourcesTemplate.opsForList().range(key, 0, size);
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