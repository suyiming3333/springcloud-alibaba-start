package com.corn.springcloud.resourceservice.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.corn.springcloud.resourceservice.permission.entity.Permission;
import com.corn.springcloud.resourceservice.permission.mapper.PermissionMapper;
import com.corn.springcloud.resourceservice.permission.service.PermissionService;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDto> getPermissionByUserId(Integer userId) {
        return permissionMapper.getPermissionByUserId(userId);
    }

    @Override
    public void addPermission(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setCode("10086");
        permission.setDescription("test at");
        permission.setUrl("wwww.10086.com");
        permissionMapper.insert(permission);
    }
}
