package cn.luokaiii.user.provider.controller;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.common.utils.CopyUtils;
import cn.luokaiii.user.api.model.BaseUser;
import cn.luokaiii.user.api.model.ResponseCode;
import cn.luokaiii.user.api.service.BaseUserRemoteService;
import cn.luokaiii.user.provider.service.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BaseUserController implements BaseUserRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaseUserService baseUserService;

    @Autowired
    public BaseUserController(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public ResponseData<BaseUser> getUserByUsername(@PathVariable("username") String username) {
        BaseUser baseUser = baseUserService.findByUsername(username)
                .orElseThrow(() -> new ResponseException("没有找到该用户"));
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), baseUser);
    }

    @Override
    public ResponseData<BaseUser> getUserByPhone(@PathVariable("phone") String phone) {
        BaseUser baseUser = baseUserService.findByPhone(phone)
                .orElseThrow(() -> new ResponseException("没有找到该用户"));
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), baseUser);
    }

    @PostMapping("/user")
    protected ResponseData<BaseUser> addRecord(@RequestBody BaseUser record) {
        logger.debug("添加用户");
        try {
            record.setPassword(new BCryptPasswordEncoder(6).encode(record.getPassword()));
            baseUserService.save(record);
        } catch (Exception e) {
            logger.error("添加用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/user/{id}")
    protected ResponseData<BaseUser> deleteRecord(@PathVariable String id) {
        logger.debug("删除用户");
        try {
            baseUserService.deleteById(id);
        } catch (Exception e) {
            logger.error("删除用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/user")
    protected ResponseData<BaseUser> updateRecord(@RequestBody BaseUser record) {
        logger.debug("更新用户");
        try {
            Optional<BaseUser> baseUser = baseUserService.findById(record.getId());
            record.setPassword(null);

            baseUser.ifPresent(user -> {
                CopyUtils.copyPropertiesIgnoreNull(record, user);
                baseUserService.save(user);
            });
        } catch (Exception e) {
            logger.error("更新用户失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
