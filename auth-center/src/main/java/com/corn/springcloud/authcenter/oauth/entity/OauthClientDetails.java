package com.corn.springcloud.authcenter.oauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端标识
     */
    @TableId(value = "client_id", type = IdType.AUTO)
    private String clientId;

    /**
     * 接入资源列表
     */
    private String resourceIds;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 允许授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 客户端的重定向URI
     */
    private String webServerRedirectUri;

    /**
     * 客户端所拥有的权限值
     */
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒)
     */
    private Integer accessTokenValidity;

    /**
     * 设定客户端的refresh_token的有效时间值(单位:秒
     */
    private Integer refreshTokenValidity;

    /**
     * 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,
     */
    private String additionalInformation;

    private LocalDateTime createTime;

    /**
     * 用于标识客户端是否已存档(即实现逻辑删除),默认值为’0’(即未存档)
     */
    private Boolean archived;

    /**
     * 设置客户端是否为受信任的,默认为’0’(即不受信任的,1为受信任的)
     */
    private Boolean trusted;

    /**
     * 设置用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’. 
     */
    private String autoapprove;


    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

}
