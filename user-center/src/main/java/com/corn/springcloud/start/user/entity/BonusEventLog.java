package com.corn.springcloud.start.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@TableName("tb_bonus_event_log")
public class BonusEventLog extends Model<BonusEventLog> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 积分值
     */
    private Integer value;

    /**
     * 积分时间
     */
    private String event;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 积分时间描述
     */
    private String description;

    /**
     * 用户id
     */
    private Integer userId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
