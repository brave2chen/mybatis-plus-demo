package com.bravechen.springboot.mybatisplusdemo.rest;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bravechen.springboot.mybatisplusdemo.entity.User;
import com.bravechen.springboot.mybatisplusdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 陈庆勇
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userService.getById(id);
    }

    @PostMapping("")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateById(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id) {
        return userService.removeById(id);
    }


    @GetMapping("/page")
    public Page<User> page(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize) {
        return userService.page(new Page<>(currentPage, pageSize));
    }
}
