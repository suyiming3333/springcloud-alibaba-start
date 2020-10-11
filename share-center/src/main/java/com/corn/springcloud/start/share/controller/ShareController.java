package com.corn.springcloud.start.share.controller;


import com.corn.springcloud.start.share.dto.ShareDto;
import com.corn.springcloud.start.share.dto.UserDto;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.service.ShareService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

