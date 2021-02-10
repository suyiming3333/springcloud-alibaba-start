package com.corn.springcloud.resourceservice.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.corn.springcloud.resourceservice.role.entity.UserRole;
import com.corn.springcloud.resourceservice.role.mapper.UserRoleMapper;
import com.corn.springcloud.resourceservice.role.service.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
