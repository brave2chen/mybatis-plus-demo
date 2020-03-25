package com.bravechen.springboot.mybatisplusdemo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bravechen.springboot.mybatisplusdemo.entity.User;
import com.bravechen.springboot.mybatisplusdemo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 陈庆勇
 * @since 2020-03-25
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
