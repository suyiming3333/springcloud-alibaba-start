package com.corn.springcloud.start.share.dto;

import com.corn.springcloud.start.enums.AuditStatusEnum;
import lombok.Data;


@Data
public class ShareAuditDto {

    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
