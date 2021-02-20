package com.corn.springcloud.start.feignclient;

import com.corn.springcloud.start.user.dto.UserAddBonusMsgDTO;
import com.corn.springcloud.start.user.dto.UserDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeignClientFallBack implements UserServiceFeignClient{
    @Override
    public UserDto findById(Integer id) {
        UserDto userDto = new UserDto();
        userDto.setWxNickname("默认的用户");
        return userDto;
    }

    @Override
    public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {

    }

    @Override
    public UserDtoV2 loadUserByUserName(String userName) {
        return null;
    }
}
