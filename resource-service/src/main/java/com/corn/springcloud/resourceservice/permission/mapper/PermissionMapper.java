package com.corn.springcloud.resourceservice.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.corn.springcloud.resourceservice.permission.entity.Permission;
import com.corn.springcloud.start.resources.dto.PermissionDto;
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

    List<PermissionDto> getPermissionByUserId(@Param("userId") Integer userId);

}
