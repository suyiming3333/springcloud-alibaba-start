package com.corn.springcloud.authcenter.security.config;

import com.corn.springcloud.authcenter.security.filter.MyAuthenticationEntryPoint;
import com.corn.springcloud.authcenter.security.filter.MyAuthenticationFilter;
import com.corn.springcloud.authcenter.security.filter.MyJwtAuthorizationFilter;
import com.corn.springcloud.authcenter.security.handler.AuthFailureHandler;
import com.corn.springcloud.authcenter.security.handler.LogoutHandler;
import com.corn.springcloud.authcenter.security.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SuccessHandler successHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .successHandler(successHandler).permitAll()
                .failureHandler(authFailureHandler).permitAll().and()
                .logout().logoutSuccessHandler(logoutHandler).and()
                .authorizeRequests()
                .antMatchers("/api/login","/test/*","/actuator/*").permitAll()
                .anyRequest().authenticated()/**所有请求都需要认证才能访问**/
                .and()
                .addFilter(new MyAuthenticationFilter(authenticationManager()))/**设置身份认证过滤器**/
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
