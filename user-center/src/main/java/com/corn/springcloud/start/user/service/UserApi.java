package com.corn.springcloud.start.user.service;

import com.corn.springcloud.start.user.dto.UserDtoV2;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface UserApi {


    /**
     * try阶段，检查用户是否存在
     * @param userDtoV2
     * @return
     */
    @TwoPhaseBusinessAction(name = "beforeAddUser", commitMethod = "addUserCommit", rollbackMethod = "addUserCancel")
    Boolean beforeAddUser(@BusinessActionContextParameter(paramName = "userDtoV2")UserDtoV2 userDtoV2);

    /**
     * 新增用户提交
     * @param actionContext
     * @return
     */
    Boolean addUserCommit(BusinessActionContext actionContext);

    /**
     * 新增用户回滚
     * @param actionContext
     * @return
     */
    Boolean addUserCancel(BusinessActionContext actionContext);
}
