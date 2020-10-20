package com.corn.springcloud.start.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 针对来源的设置
 */
//@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {

        /**
         *  可以针对 request 做很多文章
         *  来源放在参数中传递
         *  从请求参数中获取名为 origin 的参数并返回
         *  获取不到 origin 参数则抛异常
         */
        String origin = request.getParameter("origin");
        if (StringUtils.isBlank(origin)) {
            throw new IllegalArgumentException("origin must be specified");
        }

        return origin;
    }
}

