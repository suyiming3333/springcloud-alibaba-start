package com.corn.springcloud.start.user.mapper;

import com.corn.springcloud.start.user.dto.UserDtoV2;
import com.corn.springcloud.start.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.corn.springcloud.start.user.entity.User2;
import com.corn.springcloud.start.user.entity.UserEntity;
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
public interface UserMapper extends BaseMapper<User2> {

    void addUserBonus(@Param("id")Integer id,@Param("bonus")Integer bonus);

    User findUserByWxId(@Param("wxId")String wxId);

    UserDtoV2 loadUserByUserName(@Param("userName") String userName);

}
