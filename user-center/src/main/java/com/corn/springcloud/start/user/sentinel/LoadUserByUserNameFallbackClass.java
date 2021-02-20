package com.corn.springcloud.start.user.sentinel;

import com.corn.springcloud.start.user.dto.UserDtoV2;

public class LoadUserByUserNameFallbackClass {

    public static UserDtoV2 fallback(String s, Throwable e){
        System.out.println("被降级了");
        UserDtoV2 u = new UserDtoV2();
        u.setFullname("suiming");
        u.setId(1);
        u.setUsername("13265905399");
        return u;
    }
}
