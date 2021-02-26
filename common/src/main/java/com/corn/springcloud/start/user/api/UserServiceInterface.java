package com.corn.springcloud.start.user.api;

import com.corn.springcloud.start.user.dto.UserAddBonusMsgDTO;
import com.corn.springcloud.start.user.dto.UserDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import org.springframework.web.bind.annotation.*;

public interface UserServiceInterface {

    @GetMapping("/{id}")
    UserDto findById(@PathVariable(value = "id") Integer id);

    @PostMapping("/addBonus")
    void addBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO);

    @GetMapping("/loadUserByUserName/{userName}")
    UserDtoV2 loadUserByUserName(@PathVariable("userName") String userName);

    @PostMapping("/addUser")
    void addUser(@RequestBody UserDtoV2 userDtoV2);

    @PostMapping("/addUserByTcc")
    void addUserByTcc(@RequestBody UserDtoV2 userDtoV2);
}
