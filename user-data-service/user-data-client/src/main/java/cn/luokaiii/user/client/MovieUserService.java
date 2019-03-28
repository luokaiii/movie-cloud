package cn.luokaiii.user.client;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.InterfaceService;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.api.service.MovieUserRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = MovieUserService.HystrixClientFallback.class)
public interface MovieUserService extends MovieUserRemoteService {

    class HystrixClientFallback implements MovieUserService {

        @Override
        public ResponseData<MovieUser> getUserByUsername(@PathVariable("username") String username) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<MovieUser> getUserByPhone(@PathVariable("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
