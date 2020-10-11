package com.corn.springcloud.start;

import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void test(){
        List<User> userList = userService.list();
        User user = new User();
        user.setWxNickname("corn");
        userService.save(user);
        System.out.println(1);
    }
}
