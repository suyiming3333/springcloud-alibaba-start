package com.corn.springcloud.start.share.controller;

import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.dto.UserDto;
import com.corn.springcloud.start.feignclient.TestUrlFeignClient;
import com.corn.springcloud.start.feignclient.UserServiceFeignClient;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
        return this.testUrlFeignClient.baiduIndex();
    }
}

