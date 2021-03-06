package com.corn.springcloud.start.user.service;

import com.corn.springcloud.start.user.dto.UserLoginDTO;
import com.corn.springcloud.start.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
public interface UserService extends IService<User> {

    void addBonus(Integer id, int bonus);

    User login(UserLoginDTO loginDTO, String openid);
}
