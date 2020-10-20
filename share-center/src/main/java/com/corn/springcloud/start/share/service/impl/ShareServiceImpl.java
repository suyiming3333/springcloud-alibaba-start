package com.corn.springcloud.start.share.service.impl;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.corn.springcloud.start.dto.ShareAuditDto;
import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.feignclient.UserServiceFeignClient;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.mapper.ShareMapper;
import com.corn.springcloud.start.share.service.ShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;


    @SentinelResource(value = "test-service",entryType = EntryType.IN)
    @Override
    public void testService(String s) {
        System.out.println("tes -ervice invoke:"+s);
    }

    @Override
    public ShareDto auditById(Integer id, ShareAuditDto shareAuditDto) {
        //查询资源是否存在
        Share share = shareMapper.selectById(id);
        if(share == null){
            throw new IllegalArgumentException("illegal param");
        }
        if(!Objects.equals("NOT_YET",share.getAuditStatus())){
            throw new IllegalArgumentException("article already be audited");
        }
        //更新审批状态(主流程)
        share.setAuditStatus(shareAuditDto.getAuditStatusEnum().toString());
        shareMapper.updateById(share);

        //分享内容通过，添加积分(次流程)
        //异步执行 1、asyncRestTemplate 2、@Async 3、webclient(spring5.0引入) 4、MQ
        if(Objects.equals("PASS",shareAuditDto.getAuditStatusEnum().toString())){
            userServiceFeignClient.addBonus(id,500);
        }
        return null;
    }
}
