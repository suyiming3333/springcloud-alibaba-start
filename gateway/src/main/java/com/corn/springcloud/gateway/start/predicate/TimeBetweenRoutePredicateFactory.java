package com.corn.springcloud.gateway.start.predicate;

import com.corn.springcloud.gateway.start.config.TimeBetweenConfig;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义谓词工厂
 * **/
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return exchange -> {
            LocalTime now = LocalTime.now();
            //时间处于 start 和 end 之间
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    /**
     * 控制 配置类和配置文件的映射关系和顺序
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        //映射
        //上午9:00  下午5:00是Gateway的时间格式形式
        //配置文件中的上午9:00对应配置实体类中的start,下午5:00对应end
        return Arrays.asList("start","end");
    }
}
