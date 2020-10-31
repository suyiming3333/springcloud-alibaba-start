package com.corn.springcloud.gateway.start.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

@Component
@Slf4j
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return ((exchange, chain) -> {
            log.info("请求进来了 = {},{}",config.getName(),config.getValue());
            ServerHttpRequest originalRequest = exchange.getRequest();
            MultiValueMap<String, String> queryParams = originalRequest.getQueryParams();
            Map<String, String> stringStringMap = queryParams.toSingleValueMap();
            stringStringMap.entrySet().stream().forEach(v->{
                System.out.println("request params"+v.getKey()+":"+v.getValue());
            });
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

            return chain.filter(modifiedExchange);
        });
    }
}
