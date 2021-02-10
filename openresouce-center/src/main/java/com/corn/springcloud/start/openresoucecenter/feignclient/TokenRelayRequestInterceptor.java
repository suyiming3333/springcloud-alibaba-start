package com.corn.springcloud.start.openresoucecenter.feignclient;

import com.corn.springcloud.start.user.entity.UserEntity;
import com.corn.springcloud.start.utils.JwtTokenUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * feign token 传递拦截器
 */
public class TokenRelayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //1. 从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");

        //2. 传递token
        if (StringUtils.isNoneBlank(token)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String jwttoken = JwtTokenUtil.createToken(userEntity.getUsername(), authorities.toString());
            //feign client调用是的认证参数
            requestTemplate.header("Authorization","Bearer "+jwttoken);

        }
    }
}
