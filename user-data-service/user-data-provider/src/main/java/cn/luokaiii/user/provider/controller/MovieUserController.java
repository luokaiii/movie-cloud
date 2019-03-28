package cn.luokaiii.user.provider.controller;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.common.utils.CopyUtils;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieUser;
import cn.luokaiii.user.api.service.MovieUserRemoteService;
import cn.luokaiii.user.provider.service.MovieUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MovieUserController implements MovieUserRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MovieUserService movieUserService;

    @Autowired
    public MovieUserController(MovieUserService movieUserService) {
        this.movieUserService = movieUserService;
    }

    @Override
    public ResponseData<MovieUser> getUserByUsername(@PathVariable("username") String username) {
        MovieUser movieUser = movieUserService.findByUsername(username)
                .orElseThrow(() -> new ResponseException("没有找到该用户"));
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), movieUser);
    }

    @Override
    public ResponseData<MovieUser> getUserByPhone(@PathVariable("phone") String phone) {
        MovieUser movieUser = movieUserService.findByPhone(phone)
                .orElseThrow(() -> new ResponseException("没有找到该用户"));
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), movieUser);
    }

    @GetMapping("/user/list")
    public ResponseData<Page<MovieUser>> getByExample(MovieUser movieUser,
                                                      @PageableDefault Pageable pageable) {
        Example<MovieUser> example = Example.of(movieUser);
        Page<MovieUser> users = movieUserService.getByPage(example, pageable);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), users);
    }

    @PostMapping("/user")
    protected ResponseData<MovieUser> addRecord(@RequestBody MovieUser record) {
        logger.debug("添加用户");
        try {
            record.setPassword(new BCryptPasswordEncoder(6).encode(record.getPassword()));
            movieUserService.save(record);
        } catch (Exception e) {
            logger.error("添加用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/user/{id}")
    protected ResponseData<MovieUser> deleteRecord(@PathVariable String id) {
        logger.debug("删除用户");
        try {
            movieUserService.deleteById(id);
        } catch (Exception e) {
            logger.error("删除用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/user")
    protected ResponseData<MovieUser> updateRecord(@RequestBody MovieUser record) {
        logger.debug("更新用户");
        try {
            Optional<MovieUser> baseUser = movieUserService.findById(record.getId());
            record.setPassword(null);

            baseUser.ifPresent(user -> {
                CopyUtils.copyPropertiesIgnoreNull(record, user);
                movieUserService.save(user);
            });
        } catch (Exception e) {
            logger.error("更新用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
