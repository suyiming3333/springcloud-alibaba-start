package com.corn.springcloud.authcenter.web;


import com.corn.springcloud.authcenter.feign.ResourceServiceFeignClient;
import com.corn.springcloud.authcenter.feign.UserServiceFeignClient;
import com.corn.springcloud.start.resources.dto.PermissionDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestBusinessServiceImpl {

    @Autowired
    private ResourceServiceFeignClient resourceServiceFeignClient;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @GlobalTransactional(timeoutMills = 300000, name = "seata-demo-business")
    public void seataTestATmode(){
        boolean flag = true;
        log.info("开始全局事务，XID = " + RootContext.getXID());
        resourceServiceFeignClient.addPermission(new PermissionDto());
        userServiceFeignClient.addUser(new UserDtoV2());
        if (!flag) {
            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
        }
    }

    @GlobalTransactional(timeoutMills = 300000, name = "seata-tcc-demo-business")
    public void seataTestTCCmode(){
        boolean flag = true;
        log.info("开始全局事务，XID = " + RootContext.getXID());
        resourceServiceFeignClient.addPermissionByTcc(new PermissionDto());
        userServiceFeignClient.addUserByTcc(new UserDtoV2());
        if (!flag) {
            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
        }
    }
}
