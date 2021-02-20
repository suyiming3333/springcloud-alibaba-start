package com.corn.springcloud.resourceservice.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.corn.springcloud.resourceservice.permission.service.PermissionService;
import com.corn.springcloud.resourceservice.sentinel.PermissionsByIdBlockHandlerClass;
import com.corn.springcloud.resourceservice.sentinel.PermissionsByIdFallbackClass;
import com.corn.springcloud.start.resources.api.ResourceSerivceInterface;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    Environment environment;

    /***
     * 注意：
     * fallback 函数签名和位置要求：
     * 返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * @param id
     * @return
     */
    @Override
    @GetMapping("/permissions/{id}")
    @SentinelResource(
            value = "permissionsById",
            blockHandler = "permissionsblock",
            blockHandlerClass = PermissionsByIdBlockHandlerClass.class,
            fallbackClass = PermissionsByIdFallbackClass.class,
            fallback = "permissionsfallback")
    public List<PermissionDto> getPermissionByUserId(@PathVariable Integer id) {
        List<PermissionDto> permissionDtos = permissionService.getPermissionByUserId(id);
        System.out.println("resource-service invoked");
        System.out.println(environment.getProperty("local.server.port"));
//        if(1== id){
//            throw new RuntimeException("error");
//        }
        return permissionDtos;
    }
}
