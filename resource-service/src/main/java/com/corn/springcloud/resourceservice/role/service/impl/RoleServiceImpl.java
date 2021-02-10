package com.corn.springcloud.resourceservice.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.corn.springcloud.resourceservice.role.entity.Role;
import com.corn.springcloud.resourceservice.role.mapper.RoleMapper;
import com.corn.springcloud.resourceservice.role.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
