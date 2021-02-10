package com.corn.springcloud.authcenter.security.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.corn.springcloud.authcenter.feign.ResourceServiceFeignClient;
import com.corn.springcloud.authcenter.feign.UserServiceFeignClient;
import com.corn.springcloud.authcenter.permission.entity.Permission;
import com.corn.springcloud.authcenter.permission.service.PermissionService;
import com.corn.springcloud.authcenter.user.entity.User;
import com.corn.springcloud.authcenter.user.service.UserService;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceServiceFeignClient resourceServiceFeignClient;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDtoV2 user = userServiceFeignClient.loadUserByUserName(s);
        if (user == null) {
            return null;
        }
        //根据userId获取权限
//        List<Permission> permissions = permissionService.getPermissionByUserId(user.getId());
        List<PermissionDto> permissions = resourceServiceFeignClient.getPermissionByUserId(user.getId());
        List<String> codes = permissions.stream().map(PermissionDto::getCode).collect(Collectors.toList());
        String[] authorities = null;
        if (CollectionUtils.isNotEmpty(codes)) {
            authorities = new String[codes.size()];
            codes.toArray(authorities);
        }
        //身份令牌
        String principal = JSON.toJSONString(user);
        return org.springframework.security.core.userdetails.User.withUsername(principal).password(user.getPassword()).authorities(authorities).build();
//        return org.springframework.security.core.userdetails.User.withUsername(principal).password("{noop}"+user.getPassword()).authorities(authorities).build();
    }
}
