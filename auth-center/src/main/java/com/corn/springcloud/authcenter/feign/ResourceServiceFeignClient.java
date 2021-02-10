package com.corn.springcloud.authcenter.feign;

import com.corn.springcloud.start.resources.api.ResourceSerivceInterface;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "resource-service",
        path = "api/resource"
)
public interface ResourceServiceFeignClient extends ResourceSerivceInterface {
}
