package com.corn.springcloud.resourceservice.service;

import com.corn.springcloud.start.resources.dto.PermissionDto;
import com.corn.springcloud.start.user.dto.UserDtoV2;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface ResourceApi {


    /**
     * try阶段，检查用户是否存在
     * @param permissionDto
     * @return
     */
    @TwoPhaseBusinessAction(name = "beforeAddPermission", commitMethod = "addPermissionCommit", rollbackMethod = "addPermissionCancel")
    Boolean beforeAddPermission(@BusinessActionContextParameter(paramName = "permissionDto") PermissionDto permissionDto);

    /**
     * 新增用户提交
     * @param actionContext
     * @return
     */
    Boolean addPermissionCommit(BusinessActionContext actionContext);

    /**
     * 新增用户回滚
     * @param actionContext
     * @return
     */
    Boolean addPermissionCancel(BusinessActionContext actionContext);
}
