package com.corn.springcloud.start.share.entity;

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
 * @since 2020-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RocketmqTransactionLog extends Model<RocketmqTransactionLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String transactionId;

    private String log;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
