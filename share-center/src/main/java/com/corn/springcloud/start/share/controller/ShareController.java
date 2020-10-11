package com.corn.springcloud.start.share.controller;

import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.dto.UserDto;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.service.ShareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/echo/app-name")
    public String echoAppName(){
        //Access through the combination of LoadBalanceClient and RestTemplate
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-service");
        String path = String.format("http://%s:%s/users/%s",serviceInstance.getHost(),serviceInstance.getPort(),1);
        System.out.println("request path:" +path);
        return restTemplate.getForObject(path,String.class);
    }


    @GetMapping("/{id}")
    public ShareDto findByid(@PathVariable Integer id){
        Share share = shareService.getById(id);
//        String result = restTemplate.getForObject("http://localhost:8080/users/{id}", String.class,1);
        ResponseEntity<UserDto> entity = restTemplate.getForEntity("http://localhost:8080/users/{id}", UserDto.class,share.getUserId());

        ShareDto shareDto = new ShareDto();

        BeanUtils.copyProperties(share,shareDto);
        if(200 == entity.getStatusCodeValue()){
            shareDto.setWxNickname(entity.getBody().getWxNickname());
        }else{
            shareDto.setWxNickname("匿名用户");
        }

        return shareDto;
    }

}

