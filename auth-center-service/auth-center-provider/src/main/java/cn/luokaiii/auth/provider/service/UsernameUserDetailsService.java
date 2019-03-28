package cn.luokaiii.auth.provider.service;

import cn.luokaiii.auth.api.config.BaseUserDetail;
import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.client.ModuleResourcesService;
import cn.luokaiii.user.client.MovieRoleService;
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
import java.util.Collections;
import java.util.List;

@Service
public class UsernameUserDetailsService implements UserDetailsService {

    private final MovieUserService movieUserService;

    private final MovieRoleService movieRoleService;

    private final ModuleResourcesService moduleResourcesService;

    private final RedisTemplate<String, ModuleResources> resourcesTemplate;

    private final RedisTemplate<String, MovieRole> roleTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UsernameUserDetailsService(MovieUserService movieUserService,
                                      MovieRoleService movieRoleService,
                                      ModuleResourcesService moduleResourcesService,
                                      RedisTemplate<String, ModuleResources> resourcesTemplate,
                                      RedisTemplate<String, MovieRole> roleTemplate) {
        this.movieUserService = movieUserService;
        this.movieRoleService = movieRoleService;
        this.moduleResourcesService = moduleResourcesService;
        this.resourcesTemplate = resourcesTemplate;
        this.roleTemplate = roleTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 如果需要多种方式的认证，可以将getUser方法抽象化，交给子类来实现
        // 获取用户
        ResponseData<MovieUser> userData = movieUserService.getUserByUsername(s);
        if (userData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(userData.getCode())) {
            logger.error("查询用户失败！{}", s);
            throw new UsernameNotFoundException("找不到该用户 " + s);
        }
        MovieUser user = userData.getData();

        // 获取用户的角色
        ResponseData<List<MovieRole>> roleData = movieRoleService.getRoleByCodes(user.getRoles());
        if (roleData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(roleData.getCode())) {
            logger.error("查询角色失败！");
        }
        List<MovieRole> roles = roleData.getData() == null ? Collections.emptyList() : roleData.getData();

        // 获取用户的权限信息
        List<GrantedAuthority> authorities = grantedAuthorities(user.getId(), roles);

        // 获取权限对应的资源
        updateUserRoleMenus(user.getId(), roles);

        // 返回带有用户权限信息的User
        User authUser = new User(s, user.getPassword(), authorities);
        return new BaseUserDetail(user, authUser);
    }

    private List<GrantedAuthority> grantedAuthorities(String userId, List<MovieRole> roles) {
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        // 清除 Redis 中用户的角色
        roleTemplate.delete(userId);
        for (MovieRole role : roles) {
            // 将用户的role，转换成权限信息
            list.add(new SimpleGrantedAuthority(role.getRoleCode()));
            // 存储角色到redis中
            roleTemplate.opsForList().rightPush(userId, role);
        }
        return list;
    }

    private void updateUserRoleMenus(String userId, List<MovieRole> roles) {
        String key = userId + "-menu";

        String[] roleIds = roles.stream().map(MovieRole::getId).toArray(String[]::new);

        ResponseData<List<ModuleResources>> menusData = moduleResourcesService.getMenusByRoles(roleIds);
        if (menusData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(menusData.getCode())) {
            logger.error("查询资源失败！");
        }
        resourcesTemplate.delete(key);
        for (ModuleResources resources : menusData.getData()) {
            resourcesTemplate.opsForList().leftPush(key, resources);
        }
    }
}
