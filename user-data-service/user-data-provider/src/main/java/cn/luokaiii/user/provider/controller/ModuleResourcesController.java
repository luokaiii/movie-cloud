package cn.luokaiii.user.provider.controller;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.api.service.ModuleResourcesRemoteService;
import cn.luokaiii.user.provider.service.ModuleResourcesService;
import cn.luokaiii.user.provider.service.MovieRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ModuleResourcesController implements ModuleResourcesRemoteService {

    private final ModuleResourcesService moduleResourcesService;

    private final MovieRoleService movieRoleService;

    @Autowired
    public ModuleResourcesController(ModuleResourcesService moduleResourcesService,
                                     MovieRoleService movieRoleService) {
        this.moduleResourcesService = moduleResourcesService;
        this.movieRoleService = movieRoleService;
    }

    @Override
    public ResponseData<List<ModuleResources>> getMenusByRoles(@RequestParam("roleId") String[] roles) {
        if (roles == null || roles.length == 0)
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());

        List<MovieRole> movieRoles = movieRoleService.findByIds(Arrays.asList(roles));
        Set<String> resourceIds = new HashSet<>();
        movieRoles.forEach(val -> {
            if (val.getResourceIds() != null) {
                resourceIds.addAll(val.getResourceIds());
            }
        });
        List<ModuleResources> resources = moduleResourcesService.findByIds(resourceIds);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), resources);
    }

    @Override
    public ResponseData<List<ModuleResources>> getMenusByIds(@RequestParam("ids") Set<String> ids) {
        List<ModuleResources> resources = moduleResourcesService.findByIds(ids);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), resources);
    }
}
