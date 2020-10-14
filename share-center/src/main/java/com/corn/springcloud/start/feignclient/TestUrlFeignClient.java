package com.corn.springcloud.start.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "baidu",url = "http://www.baidu.com")
public interface TestUrlFeignClient {

    @GetMapping("")
    String baiduIndex();
}
