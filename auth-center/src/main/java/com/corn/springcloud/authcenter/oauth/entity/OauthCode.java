package com.corn.springcloud.authcenter.oauth.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.sql.Blob;
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
public class OauthCode extends Model<OauthCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据的创建时间
     */
    private LocalDateTime createTime;

    /**
     * 存储服务端系统生成的code的值(未加密)
     */
    private String code;

    /**
     * 存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.
     */
    private String authentication;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
