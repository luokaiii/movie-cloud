package cn.luokaiii.auth.provider.service;

import cn.luokaiii.auth.api.config.BaseUserDetail;
import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.client.MovieUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsernameUserDetailsService implements UserDetailsService {

    private final MovieUserService baseUserService;

    private final RedisTemplate<String, String> roleTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UsernameUserDetailsService(MovieUserService baseUserService, RedisTemplate<String, String> roleTemplate) {
        this.baseUserService = baseUserService;
        this.roleTemplate = roleTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 如果需要多种方式的认证，可以将getUser方法抽象化，交给子类来实现
        ResponseData<MovieUser> userData = baseUserService.getUserByUsername(s);
        if (userData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(userData.getCode())) {
            logger.error("找不到该用户 {}", s);
            throw new UsernameNotFoundException("找不到该用户 " + s);
        }
        MovieUser user = userData.getData();

        // 获取用户的权限信息
        List<GrantedAuthority> authorities = grantedAuthorities(user);

        // 返回带有用户权限信息的User
        User authUser = new User(s, user.getPassword(), authorities);
        return new BaseUserDetail(user, authUser);
    }

    private List<GrantedAuthority> grantedAuthorities(MovieUser movieUser) {
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        // 清除 Redis 中用户的角色
        roleTemplate.delete(movieUser.getId());
        for (String role : movieUser.getRoles()) {
            // 将用户的role，转换成权限信息
            list.add(new SimpleGrantedAuthority(role));
            // 存储角色到redis中
            roleTemplate.opsForList().rightPush(movieUser.getId(), role);
        }
        return list;
    }
}
