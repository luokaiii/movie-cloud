package cn.luokaiii.user.api.service;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.model.MovieRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieRoleRemoteService {

    /**
     * 根据权限ID查权限
     */
    @RequestMapping(value = "/role/user/roleIds", method = RequestMethod.GET)
    ResponseData<List<MovieRole>> getRoleByCodes(@RequestParam("codes") String[] codes);

}
