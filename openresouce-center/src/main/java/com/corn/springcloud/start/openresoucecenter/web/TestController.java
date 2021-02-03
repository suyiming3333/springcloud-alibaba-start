package com.corn.springcloud.start.openresoucecenter.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/openapi")
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
//    @RolesAllowed("user:manage")
//    @PreAuthorize("hasAnyAuthority('user:manage1')")
    public String sayHi(){
        System.out.println("helllo");
        return "hello open api";
    }
}
