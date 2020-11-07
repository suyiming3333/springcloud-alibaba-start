package com.corn.springcloud.start.share.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_mid_user_share")
@Builder
public class MidUserShare extends Model<MidUserShare> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer shareId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
