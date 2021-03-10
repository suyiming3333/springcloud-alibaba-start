package com.corn.springcloud.resourceservice.org.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
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
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_organization")
public class Organization extends Model<Organization> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String orgName;

    private String orgCode;

    private String parentId;

    private LocalDate creataTime;

    private LocalDate updateTime;

    private String orgType;

    private String remark;

    private Integer displayOrder;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
