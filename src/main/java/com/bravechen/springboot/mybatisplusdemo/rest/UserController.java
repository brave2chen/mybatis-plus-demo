package com.bravechen.springboot.mybatisplusdemo.rest;


import com.bravechen.springboot.mybatisplusdemo.entity.User;
import com.bravechen.springboot.mybatisplusdemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }
}
