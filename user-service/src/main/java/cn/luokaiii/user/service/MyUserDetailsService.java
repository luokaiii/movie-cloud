package cn.luokaiii.user.service;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.utils.CopyUtils;
import cn.luokaiii.user.dao.UserDao;
import cn.luokaiii.user.model.MyUserDetails;
import cn.luokaiii.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new ResponseException(String.format("用户没有找到 : %s", username)));

        MyUserDetails userDetails = new MyUserDetails(username);

        CopyUtils.copyPropertiesIgnoreNull(user, userDetails);
        log.info("用户 {} 登录成功", username);

        return userDetails;
    }

}
