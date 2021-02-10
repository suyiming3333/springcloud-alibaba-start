package com.corn.springcloud.start.resources.dto;

public class PermissionDto {

    private Integer id;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求地址
     */
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
