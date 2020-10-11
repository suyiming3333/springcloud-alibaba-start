package com.corn.springcloud.start.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Integer id;

    private String wxId;

    private String wxNickname;

    private String roles;

    private String avatarUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer bonus;
}
