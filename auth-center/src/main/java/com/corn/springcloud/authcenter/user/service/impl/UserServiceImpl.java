package com.corn.springcloud.authcenter.user.service.impl;

import com.corn.springcloud.authcenter.user.entity.User;
import com.corn.springcloud.authcenter.user.mapper.UserMapper;
import com.corn.springcloud.authcenter.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
