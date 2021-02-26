package com.corn.springcloud.resourceservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corn.springcloud.resourceservice.permission.entity.Permission;
import com.corn.springcloud.resourceservice.permission.service.PermissionService;
import com.corn.springcloud.resourceservice.service.ResourceApi;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceApiImpl implements ResourceApi {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Boolean beforeAddPermission(PermissionDto permissionDto) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        Permission permission = new Permission();
        permission.setCode("10086");
        queryWrapper.setEntity(permission);
        Permission one = permissionService.getOne(queryWrapper);
        System.out.println("permissionDto try");
        if(one!=null){
            return false;
        }else{
            permissionService.addPermission(permissionDto);
            return true;
        }
    }

    @Override
    public Boolean addPermissionCommit(BusinessActionContext actionContext) {
        System.out.println("permissionDto commit");
        return null;
    }

    @Override
    public Boolean addPermissionCancel(BusinessActionContext actionContext) {
        System.out.println("permissionDto cancel");
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        Permission permission = new Permission();
        permission.setCode("10086");
        queryWrapper.setEntity(permission);
        permissionService.remove(queryWrapper);
        return null;
    }
}
