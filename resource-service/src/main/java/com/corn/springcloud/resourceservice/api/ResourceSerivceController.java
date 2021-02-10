package com.corn.springcloud.resourceservice.api;

import com.corn.springcloud.resourceservice.permission.service.PermissionService;
import com.corn.springcloud.start.resources.api.ResourceSerivceInterface;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resource")
public class ResourceSerivceController implements ResourceSerivceInterface {

    @Autowired
    private PermissionService permissionService;

    @Override
    @GetMapping("/permissions/{id}")
    public List<PermissionDto> getPermissionByUserId(@PathVariable Integer id) {
        List<PermissionDto> permissionDtos = permissionService.getPermissionByUserId(id);
        return permissionDtos;
    }
}
