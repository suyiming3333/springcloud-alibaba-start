package com.corn.springcloud.authcenter.feign;


import com.corn.springcloud.start.user.api.UserServiceInterface;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "user-service",
        path = "api/users")
public interface UserServiceFeignClient extends UserServiceInterface {

    /**
     * http://user-service/users/{id}
     * @param id
     * @param token //feign调用token显式传递
     * @return
     */
//    @GetMapping("/{id}")
//    UserDto findById(@PathVariable Integer id,@RequestHeader("X-Token") String token);
}
