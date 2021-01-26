package com.corn.springcloud.authcenter.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String hello(){
        System.out.println("weather:");
        System.out.println("name:");
        return "good weather";
    }
}
