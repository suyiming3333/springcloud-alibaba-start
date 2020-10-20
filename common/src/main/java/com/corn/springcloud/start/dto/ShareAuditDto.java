package com.corn.springcloud.start.dto;

import com.corn.springcloud.start.enums.AuditStatusEnum;
import lombok.Data;


@Data
public class ShareAuditDto {

    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
