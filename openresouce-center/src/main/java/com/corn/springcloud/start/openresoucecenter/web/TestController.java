package com.corn.springcloud.start.openresoucecenter.web;

import com.corn.springcloud.start.openresoucecenter.feignclient.UserServiceFeignClient;
import com.corn.springcloud.start.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/openapi")
public class TestController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @RequestMapping("/hello")
    @ResponseBody
//    @RolesAllowed("user:manage")
//    @PreAuthorize("hasAnyAuthority('user:manage1')")
    public String sayHi(){
        System.out.println("helllo");
        UserDto byId = userServiceFeignClient.findById(1);
        return "hello open api";
    }
}
