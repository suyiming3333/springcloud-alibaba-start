package com.corn.springcloud.start.openresoucecenter.security.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.corn.springcloud.start.openresoucecenter.security.jwt.JwtTokenUtil;
import com.corn.springcloud.start.user.entity.UserEntity;
import com.corn.springcloud.start.utils.EncryptUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("json-token");
        if (StringUtils.isNotBlank(token)){
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            JSONObject jsonObject = JSON.parseObject(json);
            //获取用户身份信息、权限信息
            String principal = jsonObject.getString("principal");
            UserEntity user1 = JSONUtil.toBean(principal, UserEntity.class);
            UserEntity user = JSON.parseObject(principal, UserEntity.class);
            JSONArray tempJsonArray = jsonObject.getJSONArray("authorities");
            String[] authorities =  tempJsonArray.toArray(new String[0]);
            //身份信息、权限信息填充到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null,
                    AuthorityUtils.createAuthorityList(authorities));
            //创建details
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //将authenticationToken填充到安全上下文(security登录)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //设置用于访问内部service的jwttoken
            String jwttoken = JwtTokenUtil.createToken(user.getUsername(), authorities.toString());
            response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
        }
        filterChain.doFilter(request,response);
    }
}
