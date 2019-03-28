package cn.luokaiii.user.client;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.InterfaceService;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.api.service.ModuleResourcesRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Set;

/**
 * 使用Feign，调用远程服务。
 * 调用服务名为 user-data 的服务，fallback 表示如果服务无法调用时的熔断降级方法
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = ModuleResourcesService.HystrixClientFallback.class)
public interface ModuleResourcesService extends ModuleResourcesRemoteService {

    class HystrixClientFallback implements ModuleResourcesService {

        @Override
        public ResponseData<List<ModuleResources>> getMenusByRoles(String[] roles) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<List<ModuleResources>> getMenusByIds(Set<String> ids) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
