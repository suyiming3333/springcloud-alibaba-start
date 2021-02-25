package com.corn.springcloud.authcenter.feign;

import com.corn.springcloud.start.resources.api.ResourceSerivceInterface;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ResourceServiceFeignClientFallBackFactory implements FallbackFactory<ResourceSerivceInterface> {
    @Override
    public ResourceSerivceInterface create(Throwable throwable) {
        log.error("remote invoke error:ResourceServiceFeignClientFallBackFactory",throwable);
        return new ResourceSerivceInterface() {
            @Override
            public List<PermissionDto> getPermissionByUserId(Integer id) {
                return new ArrayList<>();
            }

            @Override
            public void addPermission(PermissionDto permissionDto) {

            }
        };
    }
}
