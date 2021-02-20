package com.corn.springcloud.resourceservice.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.corn.springcloud.start.resources.dto.PermissionDto;

import java.util.ArrayList;
import java.util.List;

public class PermissionsByIdBlockHandlerClass {

    public static List<PermissionDto> permissionsblock(Integer s, BlockException e){
        System.out.println("被限流了");
        return new ArrayList<PermissionDto>();
    }
}
