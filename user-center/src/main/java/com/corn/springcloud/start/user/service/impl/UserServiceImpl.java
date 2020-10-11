package com.corn.springcloud.start.user.service.impl;

import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.mapper.UserMapper;
import com.corn.springcloud.start.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
