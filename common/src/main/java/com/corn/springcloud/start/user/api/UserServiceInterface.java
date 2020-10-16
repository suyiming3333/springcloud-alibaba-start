package com.corn.springcloud.start.user.api;

import com.corn.springcloud.start.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserServiceInterface {

    @GetMapping("/{id}")
    UserDto findById(@PathVariable(value = "id") Integer id);
}
