package cn.luokaiii.user.controller;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.utils.CopyUtils;
import cn.luokaiii.user.model.User;
import cn.luokaiii.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @ApiOperation(value = "查询用户列表", tags = "管理员权限接口")
    public ResponseEntity getAll(User user, @PageableDefault Pageable pageable) {
        Example<User> example = Example.of(user);
        return ResponseEntity.ok(userService.getByPage(example, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询单个用户", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！")));
    }

    @PostMapping
    @ApiOperation(value = "创建用户", tags = "管理员权限接口")
    public ResponseEntity create(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户", tags = "用户接口")
    public ResponseEntity register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户资料", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity updateById(@PathVariable String id,
                                     @RequestBody User user) {
        User dbUser = userService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！"));
        CopyUtils.copyPropertiesIgnoreNull(user, dbUser);
        return ResponseEntity.ok(userService.save(dbUser));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除/注销用户", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity deleteById(@PathVariable String id) {
        userService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！"));
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
