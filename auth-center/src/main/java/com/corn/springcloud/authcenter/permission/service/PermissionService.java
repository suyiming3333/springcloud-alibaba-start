package com.corn.springcloud.authcenter.permission.service;

import com.corn.springcloud.authcenter.permission.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<Permission> getPermissionByUserId(Integer userId);

}
