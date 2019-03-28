package cn.luokaiii.user.api.service;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.model.ModuleResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ModuleResourcesRemoteService {

    /**
     * 根据角色查询菜单
     */
    @GetMapping("/menu/role")
    ResponseData<List<ModuleResources>> getMenusByRoles(@RequestParam("roleId") String[] roles);

}
