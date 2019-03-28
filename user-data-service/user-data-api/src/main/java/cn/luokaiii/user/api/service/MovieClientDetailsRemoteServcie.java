package cn.luokaiii.user.api.service;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.model.MovieClientDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface MovieClientDetailsRemoteServcie {

    /**
     * 获得所有的客户端信息
     */
    @GetMapping("/client/all")
    ResponseData<List<MovieClientDetails>> getAllClient();
}
