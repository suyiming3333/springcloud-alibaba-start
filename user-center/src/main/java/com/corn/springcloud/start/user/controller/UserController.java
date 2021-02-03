package com.corn.springcloud.start.user.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.corn.springcloud.start.security.auth.CheckLogin;
import com.corn.springcloud.start.user.dto.*;
import com.corn.springcloud.start.user.api.UserServiceInterface;
import com.corn.springcloud.start.user.entity.BonusEventLog;
import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.service.BonusEventLogService;
import com.corn.springcloud.start.user.service.UserService;
import com.corn.springcloud.start.utils.JwtOperator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */

@Slf4j
@RestController
@RequestMapping("/api/users")
//配置中心动态修改后自动刷新
@RefreshScope
public class UserController implements UserServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private JwtOperator jwtOperator;

    @Autowired
    private BonusEventLogService bonusEventLogService;

    @Value("${my.weather}")
    private String todayWeather;

    @Value("${first-name}")
    private String firstName;

    @Value("${last-name}")
    private String lastName;


    @RequestMapping(value = "/test")
    public String hello(){
        System.out.println("weather:"+todayWeather);
        System.out.println("name:"+firstName+""+lastName);
        return todayWeather;
    }


    @Override
    @GetMapping("/{id}")
//    @CheckLogin
    @RolesAllowed("admin")
    @PreAuthorize("hasAnyAuthority('teacher','student')")
    public UserDto findById(@PathVariable Integer id){
        User user = userService.getById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        System.out.println("user-service invoked");
        return userDto;
    }

    /**
     * 模拟生成token(假的登录)
     */
    @GetMapping("/gen-token")
    public String genToken() {
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", 1);
        userInfo.put("wxNickname", "cornsu");
        userInfo.put("role", "admin");
        return this.jwtOperator.generateToken(userInfo);
    }

    @Override
    @PostMapping("/addBonus")
    public void addBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
        Integer userId = userAddBonusMsgDTO.getUserId();
        Integer bonus = userAddBonusMsgDTO.getBonus();
        String event = userAddBonusMsgDTO.getEvent();
        String description = userAddBonusMsgDTO.getDescription();
        //添加用户积分
        userService.addBonus(userId,bonus);
        //添加用户积分事件记录
        BonusEventLog bonusEventLog = new BonusEventLog();
        bonusEventLog.setUserId(userId);
        bonusEventLog.setValue(bonus);
        bonusEventLog.setEvent(event);
        bonusEventLog.setDescription(description);
        bonusEventLog.setCreateTime(LocalDateTime.now());
        bonusEventLogService.save(bonusEventLog);

    }


    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        // 微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(loginDTO.getCode());

        // 微信的openId，用户在微信这边的唯一标示
        String openid = result.getOpenid();

        // 看用户是否注册，如果没有注册就（插入）
        // 如果已经注册
        User user = this.userService.login(loginDTO, openid);

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info(
                "用户{}登录成功，生成的token = {}, 有效期到:{}",
                loginDTO.getWxNickname(),
                token,
                jwtOperator.getExpirationTime()
        );

        // 构建响应
        return LoginRespDTO.builder()
                .user(
                        UserRespDTO.builder()
                                .id(user.getId())
                                .avatarUrl(user.getAvatarUrl())
                                .bonus(user.getBonus())
                                .wxNickname(user.getWxNickname())
                                .build()
                )
                .token(
                        JwtTokenRespDTO.builder()
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .token(token)
                                .build()
                )
                .build();
    }

    @GetMapping("/me")
    @CheckLogin
    public UserRespDTO me(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        User user = userService.getById(userId);
        return UserRespDTO.builder()
                .id(user.getId())
                .avatarUrl(user.getAvatarUrl())
                .bonus(user.getBonus())
                .wxNickname(user.getWxNickname())
                .build();
    }
}


