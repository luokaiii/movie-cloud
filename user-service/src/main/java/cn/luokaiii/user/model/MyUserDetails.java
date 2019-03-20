package cn.luokaiii.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义的UserDetails，可以放在Authentication的Principal 中
 * 结合当前的用户信息，生成完整的 UserDetails
 */
@Getter
@Setter
public class MyUserDetails extends User implements UserDetails {

    private String loginName;

    public MyUserDetails(String loginName) {
        this.loginName = loginName;
    }

    // 将用户权限转换为 Authority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = getRoles();

        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    // 密码，这里不能被隐藏，否则 AuthServer 无法去匹配 Password 模式中的账号是否正确
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 授权是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
