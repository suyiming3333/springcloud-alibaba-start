package com.corn.springcloud.start.feignclient;


import com.corn.springcloud.start.user.api.UserServiceInterface;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "user-service",
        path = "users",
//        fallback = UserServiceFeignClientFallBack.class,
        fallbackFactory = UserServiceFeignClientFallBackFactory.class)
public interface UserServiceFeignClient extends UserServiceInterface {

    /**
     * http://user-service/users/{id}
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    UserDto findById(@PathVariable Integer id);
}
