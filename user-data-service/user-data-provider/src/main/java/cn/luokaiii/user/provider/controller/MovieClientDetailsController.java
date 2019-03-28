package cn.luokaiii.user.provider.controller;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieClientDetails;
import cn.luokaiii.user.api.service.MovieClientDetailsRemoteServcie;
import cn.luokaiii.user.provider.service.MovieClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieClientDetailsController implements MovieClientDetailsRemoteServcie {

    private final MovieClientDetailsService movieClientDetailsService;

    @Autowired
    public MovieClientDetailsController(MovieClientDetailsService movieClientDetailsService) {
        this.movieClientDetailsService = movieClientDetailsService;
    }

    @Override
    public ResponseData<List<MovieClientDetails>> getAllClient() {
        List<MovieClientDetails> details = movieClientDetailsService.getBySort(Sort.by("createTime"));
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), details);
    }

}
