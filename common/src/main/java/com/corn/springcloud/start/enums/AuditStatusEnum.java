package com.corn.springcloud.start.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    NOT_YET,
    PASS,
    REJECT
}
