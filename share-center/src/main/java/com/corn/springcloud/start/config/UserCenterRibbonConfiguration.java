package com.corn.springcloud.start.config;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * 配置user-service调用时候的负载均衡策略(局部)
 * TODO:不生效，先屏蔽了
 */
//@Configuration
//@RibbonClient(name = "user-service",configuration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {
}
