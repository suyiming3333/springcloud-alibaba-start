package com.corn.springcloud.start.user.mapper;

import com.corn.springcloud.start.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import sun.rmi.server.InactiveGroupException;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
public interface UserMapper extends BaseMapper<User> {

    void addUserBonus(@Param("id")Integer id,@Param("bonus")Integer bonus);

}
