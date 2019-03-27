package cn.luokaiii.user.client;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.InterfaceService;
import cn.luokaiii.user.api.model.BaseUser;
import cn.luokaiii.user.api.model.ResponseCode;
import cn.luokaiii.user.api.service.BaseUserRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseUserService.HystrixClientFallback.class)
public interface BaseUserService extends BaseUserRemoteService {

    class HystrixClientFallback implements BaseUserService {

        @Override
        public ResponseData<BaseUser> getUserByUsername(@PathVariable("username") String username) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<BaseUser> getUserByPhone(@PathVariable("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
