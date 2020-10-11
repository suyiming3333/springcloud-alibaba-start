package com.corn.springcloud.start.dto;


import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Data
public class ShareDto {

    private Integer id;

    private Integer userId;

    private String title;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isOriginal;

    private String author;

    private String cover;

    private String summary;

    private Integer price;

    private String downloadUrl;

    private Integer buyCount;

    private Boolean showFlag;

    private String auditStatus;

    private String reason;

    private String wxNickname;

}
