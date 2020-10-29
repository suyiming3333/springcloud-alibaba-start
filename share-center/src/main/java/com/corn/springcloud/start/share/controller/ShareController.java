package com.corn.springcloud.start.share.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.ResourceTypeConstants;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.dto.UserDto;
import com.corn.springcloud.start.feignclient.TestUrlFeignClient;
import com.corn.springcloud.start.feignclient.UserServiceFeignClient;
import com.corn.springcloud.start.sentinel.BlockHandlerClass;
import com.corn.springcloud.start.sentinel.FallbackClass;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */

@Slf4j
@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private TestUrlFeignClient testUrlFeignClient;

    @Autowired
    private Source source;

    @GetMapping("/echo/app-name")
    public String echoAppName(){
        //Access through the combination of LoadBalanceClient and RestTemplate
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-service");
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-service");
        String path = String.format("http://%s:%s/users/%s",serviceInstance.getHost(),serviceInstance.getPort(),1);
        System.out.println("request path:" +path);
        //根据服务id查找示例列表
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-service");
        //查找注册中心已注册的服务列表
        List<String> clientServices = discoveryClient.getServices();
        return restTemplate.getForObject(path,String.class);
    }


    @GetMapping("/{id}")
    public ShareDto findByid(@PathVariable Integer id){
        Share share = shareService.getById(id);

        //根据服务id查找示例列表
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-service");
        String path = serviceInstances.stream()
                .map(serviceInstance -> serviceInstance.getUri() + "/users/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("找不到服务"));

        log.info("服务调用地址：{}",path);
//        String result = restTemplate.getForObject("http://localhost:8080/users/{id}", String.class,1);
//        ResponseEntity<UserDto> entity = restTemplate.getForEntity("http://localhost:8080/users/{id}", UserDto.class,share.getUserId());
//        ResponseEntity<UserDto> entity = restTemplate.getForEntity(path, UserDto.class,share.getUserId());

        //通过user-service服务id使用远程调用(已实现负载均衡)
//        ResponseEntity<UserDto> entity = restTemplate.getForEntity("http://user-service/users/{id}", UserDto.class,share.getUserId());

        UserDto userDto = userServiceFeignClient.findById(share.getUserId());
        ShareDto shareDto = new ShareDto();

        BeanUtils.copyProperties(share,shareDto);
        shareDto.setWxNickname(userDto.getWxNickname());
//        if(200 == entity.getStatusCodeValue()){
//            shareDto.setWxNickname(entity.getBody().getWxNickname());
//        }else{
//            shareDto.setWxNickname("匿名用户");
//        }

        return shareDto;
    }


    @GetMapping("/baidu")
    public String baiduIndex() {
//        return this.testUrlFeignClient.baiduIndex();
        shareService.testService("a");
        return "baidu";
    }


    @GetMapping("/testa")
    public String test() {
        shareService.testService("a");
        return "testa";
    }

    @GetMapping("/testb")
    public String testb() {
        shareService.testService("b");
        return "testb";
    }


    /**
     * 测试热点规则配置
     * @param a
     * @param b
     * @return
     */
    @GetMapping("/testHot")
    @SentinelResource("hot")
    public String testHot(@RequestParam(required = false) String a,@RequestParam(required = false) String b) {
        return a+":"+b;
    }


    @GetMapping("testSentinelApi")
    public String testSentinelApi(@RequestParam(required = false) String p){
        String resourceName = "test-sentinel-api";
        //设置调用来源
        ContextUtil.enter(resourceName,"test");

        Entry entry = null;

        try {
            //定义资源、设置要保护的资源
            entry = SphU.entry(resourceName);

            if(StringUtil.isBlank(p)){
                //异常统计只针对BlockException，所以这里无论报错多少次，都不降级，需要手动统计
                throw new IllegalArgumentException("参数为空");
            }
            return p;

        }
        catch (IllegalArgumentException e){
            //sentinel-api手动统计异常
            Tracer.trace(e);
            return "参数为空";
        }
        catch (BlockException e) {
            System.out.println("被限流或者降级了");
            e.printStackTrace();
            return "被限流或者降级了";
        }finally {
            if(entry!=null){
                entry.exit();
            }
            ContextUtil.exit();
        }

    }

    /**
     * @SentinelResource 统计throwable异常，不需要手动trace
     * @param p
     * @return
     */
    @GetMapping("testSentinelResource")
    @SentinelResource(
            value = "testSentinelResource",
            blockHandler = "block",
            blockHandlerClass = BlockHandlerClass.class,
            fallbackClass = FallbackClass.class,
            fallback = "fallback")
    public String testSentinelResource(@RequestParam(required = false) String p){

            if(StringUtil.isBlank(p)){
                //@SentinelResource 统计throwable异常，不需要手动trace手动统计
                throw new IllegalArgumentException("参数为空");
            }
            return p;
    }

    @GetMapping("/testSourceStream")
    public String testSourceStream() {
        source.output().send(MessageBuilder.withPayload("test message").build());
        return "testSourceStream ok";
    }
}

