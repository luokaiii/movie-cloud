package cn.luokaiii.user.client;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.InterfaceService;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieClientDetails;
import cn.luokaiii.user.api.service.MovieClientDetailsRemoteServcie;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = MovieClientDetailsService.HystrixClientFallback.class)
public interface MovieClientDetailsService extends MovieClientDetailsRemoteServcie {

    class HystrixClientFallback implements MovieClientDetailsService {

        @Override
        public ResponseData<List<MovieClientDetails>> getAllClient() {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
