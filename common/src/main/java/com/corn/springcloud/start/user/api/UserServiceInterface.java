package com.corn.springcloud.start.user.api;

import com.corn.springcloud.start.user.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserServiceInterface {

    @GetMapping("/{id}")
    UserDto findById(@PathVariable(value = "id") Integer id);

    @PostMapping("/addBonus")
    void addBonus(@RequestParam("userId") Integer userId,@RequestParam("bonus") int bonus);
}
