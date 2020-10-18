package com.corn.springcloud.start;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.corn.springcloud.start.config.NacosFinalRule;
import com.corn.springcloud.start.config.NacosSameClusterWeightedRule;
import com.corn.springcloud.start.config.NacosWeightedRule;
import com.corn.springcloud.start.sentinel.BlockHandlerClass;
import com.corn.springcloud.start.sentinel.FallbackClass;
import com.corn.springcloud.start.sentinel.SintinelRestTemplateErrorHandler;
import com.netflix.loadbalancer.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.corn.springcloud.start.share.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ShareCenterApplication {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            blockHandlerClass = SintinelRestTemplateErrorHandler.class,
            blockHandler = "block",
            fallbackClass = SintinelRestTemplateErrorHandler.class,
            fallback = "fallback"
    )
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 负载均衡规则：服务优先(全局配置)
     * **/
    @Bean
    public IRule iRule(){
//        return new BestAvailableRule();
//        return new NacosWeightedRule();
//        return new NacosSameClusterWeightedRule();
        return new NacosFinalRule();
    }

    /**
     * 排除ping不同的服务地址
     * **/
    @Bean
    public IPing iPing(){
        return new PingUrl();
    }

    public static void main(String[] args) {
        SpringApplication.run(ShareCenterApplication.class, args);
//        new Thread(new TestThread()).start();
    }


    static class TestThread implements Runnable{

        RestTemplate restTemplate = new RestTemplate();

        @Override
        public void run() {
            while(true){
                restTemplate.getForObject("http://localhost:8090/shares/baidu",String.class);
                System.out.println("invoke 1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
