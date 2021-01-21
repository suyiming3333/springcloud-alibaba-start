package com.corn.springcloud.authcenter.permission.mapper;

import com.corn.springcloud.authcenter.permission.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getPermissionByUserId(@Param("userId") Integer userId);

}
