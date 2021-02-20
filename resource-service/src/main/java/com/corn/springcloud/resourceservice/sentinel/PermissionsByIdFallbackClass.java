package com.corn.springcloud.resourceservice.sentinel;

import com.corn.springcloud.start.resources.dto.PermissionDto;

import java.util.ArrayList;
import java.util.List;

public class PermissionsByIdFallbackClass {

    public static List<PermissionDto> permissionsfallback(Integer s, Throwable e){
        System.out.println("被降级了");
        return new ArrayList<PermissionDto>();
    }
}
