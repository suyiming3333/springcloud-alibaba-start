package com.corn.springcloud.start.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareContributeDto {
    private Boolean isOriginal;
    private String title;
    private String author;
    private String summary;
    private Integer price;
    private String downloadUrl;
}
