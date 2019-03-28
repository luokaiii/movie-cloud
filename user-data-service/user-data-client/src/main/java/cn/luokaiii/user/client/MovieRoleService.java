package cn.luokaiii.user.client;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.InterfaceService;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieRole;
import cn.luokaiii.user.api.service.MovieRoleRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = MovieRoleService.HystrixClientFallback.class)
public interface MovieRoleService extends MovieRoleRemoteService {

    class HystrixClientFallback implements MovieRoleService {

        @Override
        public ResponseData<List<MovieRole>> getRoleByCodes(String[] codes) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
