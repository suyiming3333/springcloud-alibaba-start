package com.corn.springcloud.start.user.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.corn.springcloud.start.user.dto.UserDtoV2;

public class LoadUserByUserNameBlockHandlerClass {

    public static UserDtoV2 block(String s, BlockException e){
        System.out.println("被限流了");
        UserDtoV2 u = new UserDtoV2();
        u.setFullname("suiming");
        u.setId(1);
        u.setUsername("13265905399");
        return u;
    }
}
