package com.corn.springcloud.start.feignclient;


import com.corn.springcloud.start.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    /**
     * http://user-service/users/{id}
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDto findById(@PathVariable Integer id);
}
