package cn.luokaiii.movie.controller;

import cn.luokaiii.common.exception.ResponseException;
import cn.luokaiii.common.utils.CopyUtils;
import cn.luokaiii.movie.model.Movie;
import cn.luokaiii.movie.service.MovieService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ApiOperation(value = "查询用户列表", tags = "管理员权限接口")
    public ResponseEntity getAll(Movie movie, @PageableDefault Pageable pageable) {
        Example<Movie> example = Example.of(movie);
        return ResponseEntity.ok(movieService.getByPage(example, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询单个用户", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity getById(@PathVariable String id) {
        return ResponseEntity.ok(movieService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！")));
    }

    @PostMapping
    @ApiOperation(value = "创建用户", tags = "管理员权限接口")
    public ResponseEntity create(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户资料", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity updateById(@PathVariable String id,
                                     @RequestBody Movie movie) {
        Movie dbMovie = movieService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！"));
        CopyUtils.copyPropertiesIgnoreNull(movie, dbMovie);
        return ResponseEntity.ok(movieService.save(dbMovie));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除/注销用户", tags = {"管理员权限接口", "用户接口"})
    public ResponseEntity deleteById(@PathVariable String id) {
        movieService.findById(id).orElseThrow(() -> new ResponseException("当前用户信息异常，无法查询！"));
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
