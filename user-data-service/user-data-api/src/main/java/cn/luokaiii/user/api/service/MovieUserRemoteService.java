package cn.luokaiii.user.api.service;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.model.MovieUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface MovieUserRemoteService {

    // 根据用户名查询用户
    @RequestMapping(value = "/user/name/{username}", method = RequestMethod.GET)
    ResponseData<MovieUser> getUserByUsername(@PathVariable String username);

    // 根据手机号查询用户
    @RequestMapping(value = "/user/phone/{phone}", method = RequestMethod.GET)
    ResponseData<MovieUser> getUserByPhone(@PathVariable String phone);
}
