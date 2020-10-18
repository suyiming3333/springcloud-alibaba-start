package com.corn.springcloud.start.feignclient;

import com.corn.springcloud.start.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeignClientFallBack implements UserServiceFeignClient{
    @Override
    public UserDto findById(Integer id) {
        UserDto userDto = new UserDto();
        userDto.setWxNickname("默认的用户");
        return userDto;
    }
}
