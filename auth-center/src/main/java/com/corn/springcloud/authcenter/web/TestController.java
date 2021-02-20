package com.corn.springcloud.authcenter.web;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.corn.springcloud.authcenter.sentinel.BlockHandlerClass;
import com.corn.springcloud.authcenter.sentinel.FallbackClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /***
     * 流控需要配置到端点testSentinelResource才能触发blockHandlerClass
     * @param p
     * @return
     */
    @GetMapping("/testSentinelResource")
    @SentinelResource(
            value = "testSentinelResource",
            blockHandler = "block",
            blockHandlerClass = BlockHandlerClass.class,
            fallbackClass = FallbackClass.class,
            fallback = "fallback")
    @ResponseBody
    public String testSentinelResource(@RequestParam(required = false) String p){

        if(StringUtil.isBlank(p)){
            //@SentinelResource 统计throwable异常，不需要手动trace手动统计
            throw new IllegalArgumentException("参数为空");
        }
        return p;
    }
}
