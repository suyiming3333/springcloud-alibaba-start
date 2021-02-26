package com.corn.springcloud.start.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import com.corn.springcloud.start.user.entity.User2;
import com.corn.springcloud.start.user.mapper.UserMapper;
import com.corn.springcloud.start.user.service.UserApi;
import com.corn.springcloud.start.user.service.UserService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserApiImpl implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public Boolean beforeAddUser(UserDtoV2 userDtoV2) {
        UserDtoV2 userDtoV21 = userService.loadUserByUserName("15914343735");
        System.out.println("user try");
        if(userDtoV21!=null){
            return false;
        }else{
            userService.addUser(new UserDtoV2());
            return true;
        }
    }

    @Override
    public Boolean addUserCommit(BusinessActionContext actionContext) {
        System.out.println("user commit");
        return true;
    }

    @Override
    public Boolean addUserCancel(BusinessActionContext actionContext) {
        QueryWrapper<User2> queryWrapper = new QueryWrapper<User2>();
        User2 user2 = new User2();
        user2.setUsername("15914343735");
        queryWrapper.setEntity(user2);
        userService.remove(queryWrapper);
        System.out.println("user cancel");
        return true;
    }
}
