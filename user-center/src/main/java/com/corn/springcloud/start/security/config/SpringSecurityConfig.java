package com.corn.springcloud.start.security.config;

import com.corn.springcloud.start.security.filter.MyAuthenticationEntryPoint;
import com.corn.springcloud.start.security.filter.MyJwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
/**开启三种方式的注解权限**/
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()/*允许跨域请求*/
                .and()
                .csrf().disable()/*先关闭跨站请求伪造，除非设置refer,否则post请求不可访问*/
                .authorizeRequests()/**定义哪些URL需要被保护、哪些不需要被保护**/
                .anyRequest().authenticated()/**所有请求都需要认证才能访问**/
                .and()
                .addFilter(new MyJwtAuthorizationFilter(authenticationManager()))/**设置请求鉴权过滤器**/
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security永不创建HttpSession 不使用HttpSession来获取SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling()
                // 匿名用户访问无权限资源时的异常
                .authenticationEntryPoint(new MyAuthenticationEntryPoint());
    }
}
