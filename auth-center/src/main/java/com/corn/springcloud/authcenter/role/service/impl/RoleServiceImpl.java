package com.corn.springcloud.authcenter.role.service.impl;

import com.corn.springcloud.authcenter.role.entity.Role;
import com.corn.springcloud.authcenter.role.mapper.RoleMapper;
import com.corn.springcloud.authcenter.role.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
