package com.corn.springcloud.start.resources.api;

import com.corn.springcloud.start.resources.dto.PermissionDto;
import com.corn.springcloud.start.user.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ResourceSerivceInterface {


    @GetMapping("/permissions/{id}")
    List<PermissionDto> getPermissionByUserId(@PathVariable(value = "id") Integer id);
}
