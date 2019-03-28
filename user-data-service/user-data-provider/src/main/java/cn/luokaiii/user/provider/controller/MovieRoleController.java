package cn.luokaiii.user.provider.controller;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.api.service.MovieRoleRemoteService;
import cn.luokaiii.user.provider.service.MovieRoleService;
import cn.luokaiii.user.provider.service.MovieUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieRoleController implements MovieRoleRemoteService {

    private final MovieRoleService movieRoleService;

    private final MovieUserService movieUserService;

    @Autowired
    public MovieRoleController(MovieRoleService movieRoleService,
                               MovieUserService movieUserService) {
        this.movieRoleService = movieRoleService;
        this.movieUserService = movieUserService;
    }

    public ResponseData<List<MovieRole>> getRoleByUserId(@PathVariable("userId") String userId) {
        MovieUser movieUser = movieUserService.findById(userId)
                .orElseThrow(() -> new ResponseException("未找到当前用户!"));

        String[] roleIds = movieUser.getRoles();
        List<MovieRole> roles = movieRoleService.findByCodeIn(roleIds);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), roles);
    }

    @Override
    public ResponseData<List<MovieRole>> getRoleByCodes(@RequestParam("codes") String[] codes) {
        List<MovieRole> roles = movieRoleService.findByCodeIn(codes);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), roles);
    }
}
