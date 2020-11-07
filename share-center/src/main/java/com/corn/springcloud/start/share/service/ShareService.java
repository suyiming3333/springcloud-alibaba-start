package com.corn.springcloud.start.share.service;

import com.corn.springcloud.start.dto.ShareAuditDto;
import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.share.entity.Share;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
public interface ShareService extends IService<Share> {

    void testService(String s);

    ShareDto auditById(Integer id, ShareAuditDto shareAuditDto);

    ShareDto auditByIdInDBWithRocketMqLog(Integer id, ShareAuditDto shareAuditDto, String transactionId);

    PageInfo<Share> q(String title, Integer pageNo, Integer pageSize, Integer userId);

    Share exchangeById(Integer id,Integer userId);
}
