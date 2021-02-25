package com.corn.springcloud.start.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import com.corn.springcloud.start.user.dto.UserLoginDTO;
import com.corn.springcloud.start.user.entity.User;
import com.corn.springcloud.start.user.entity.User2;
import com.corn.springcloud.start.user.entity.UserEntity;
import com.corn.springcloud.start.user.mapper.UserMapper;
import com.corn.springcloud.start.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User2> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addBonus(Integer id, int bonus) {
        //添加用户积分
        userMapper.addUserBonus(id,bonus);
    }

    @Override
    public User login(UserLoginDTO loginDTO, String openid) {
        User user = userMapper.findUserByWxId(openid);
        //数据库无用户记录，新增用户
        if(user == null){
            User saveUser = User.builder()
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .bonus(300)
                    .roles("USER")
                    .createTime(LocalDateTime.now())
                    .wxId(openid)
                    .wxNickname(loginDTO.getWxNickname())
                    .updateTime(LocalDateTime.now())
                    .build();
//            userMapper.insert(saveUser);
            return saveUser;
        }
        return user;
    }

    @Override
    public UserDtoV2 loadUserByUserName(String userName) {
        return userMapper.loadUserByUserName(userName);
    }

    @Override
    public void addUser(UserDtoV2 userDtoV2) {
        User2 user = new User2();
        user.setFullname("test ta user");
        user.setPassword("66666");
        user.setUsername("15914343735");
        userMapper.insert(user);
    }
}
