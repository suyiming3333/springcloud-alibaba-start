package com.corn.springcloud.start.feignclient;

import com.corn.springcloud.start.user.dto.UserAddBonusMsgDTO;
import com.corn.springcloud.start.user.dto.UserDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceFeignClientFallBackFactory implements FallbackFactory<UserServiceFeignClient> {
    @Override
    public UserServiceFeignClient create(Throwable throwable) {
        log.error("remote invoke error:",throwable);
        return new UserServiceFeignClient() {
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
        };
    }
}
