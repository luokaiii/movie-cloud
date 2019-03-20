package cn.luokaiii.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthorizationController {

    private final TokenStore tokenStore;

    @Autowired
    public AuthorizationController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @GetMapping("/ping")
    @ApiOperation(value = "获取当前登录的用户信息", tags = "授权接口")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/oauth/remove_token")
    @ApiOperation(value = "移除/退出Token", tags = "授权接口")
    public void removeToken(@RequestParam("token") String token) {
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(token);
        tokenStore.removeAccessToken(accessToken);
    }
}
