package com.corn.springcloud.start.user.controller;


import com.corn.springcloud.start.dto.UserDto;
import com.corn.springcloud.start.user.api.UserServiceInterface;
import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/users")
public class UserController implements UserServiceInterface {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/test")
    public String hello(){
        return "heelo";
    }


    @Override
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Integer id){
        User user = userService.getById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        System.out.println("user-service invoked");
        return userDto;
    }
}


