package com.corn.springcloud.resourceservice.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.corn.springcloud.resourceservice.permission.entity.Permission;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
public interface PermissionService extends IService<Permission> {

    List<PermissionDto> getPermissionByUserId(Integer userId);

    void addPermission(PermissionDto permissionDto);

}
