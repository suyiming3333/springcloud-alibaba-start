package com.corn.springcloud.start.user.service;

import com.corn.springcloud.start.user.dto.UserDtoV2;
import com.corn.springcloud.start.user.dto.UserLoginDTO;
import com.corn.springcloud.start.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.corn.springcloud.start.user.entity.User2;
import com.corn.springcloud.start.user.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
public interface UserService extends IService<User2> {

    void addBonus(Integer id, int bonus);

    User login(UserLoginDTO loginDTO, String openid);

    UserDtoV2 loadUserByUserName(String userName);

    void addUser(UserDtoV2 userDtoV2);
}
