package com.corn.springcloud.start.user.controller;


import com.corn.springcloud.start.dto.UserDto;
import com.corn.springcloud.start.user.api.UserServiceInterface;
import com.corn.springcloud.start.user.entity.BonusEventLog;
import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.service.BonusEventLogService;
import com.corn.springcloud.start.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/users")
public class UserController implements UserServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private BonusEventLogService bonusEventLogService;


    @RequestMapping(value = "/test")
    public String hello(){
        return "heelo";
    }


    @Override
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Integer id){
        User user = userService.getById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        System.out.println("user-service invoked");
        return userDto;
    }

    @Override
    @PostMapping("/addBonus")
    public void addBonus(
            @RequestParam("userId") Integer userId,
            @RequestParam("bonus") int bonus) {
        //添加用户积分
        userService.addBonus(userId,bonus);
        //添加用户积分事件记录
        BonusEventLog bonusEventLog = new BonusEventLog();
        bonusEventLog.setUserId(userId);
        bonusEventLog.setValue(bonus);
        bonusEventLog.setEvent("test event");
        bonusEventLog.setDescription("test 4");
        bonusEventLog.setCreateTime(LocalDateTime.now());
        bonusEventLogService.save(bonusEventLog);

    }
}


