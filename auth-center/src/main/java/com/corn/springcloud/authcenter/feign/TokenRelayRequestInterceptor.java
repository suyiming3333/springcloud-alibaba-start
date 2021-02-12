package com.corn.springcloud.authcenter.feign;

import com.corn.springcloud.start.utils.JwtTokenUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign token 传递拦截器
 */
public class TokenRelayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwttoken = JwtTokenUtil.createToken("suyiming", "admin");
        //feign client调用是的认证参数
        //todo 添加到缓存里，不用每一次都新创建一个token
        requestTemplate.header("Authorization","Bearer "+jwttoken);
    }
}
