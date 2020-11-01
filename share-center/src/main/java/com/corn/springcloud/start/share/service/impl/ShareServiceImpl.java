package com.corn.springcloud.start.share.service.impl;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.corn.springcloud.start.dto.ShareAuditDto;
import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.user.dto.UserAddBonusMsgDTO;
import com.corn.springcloud.start.feignclient.UserServiceFeignClient;
import com.corn.springcloud.start.share.entity.RocketmqTransactionLog;
import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.mapper.RocketmqTransactionLogMapper;
import com.corn.springcloud.start.share.mapper.ShareMapper;
import com.corn.springcloud.start.share.service.ShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    @SentinelResource(value = "test-service",entryType = EntryType.IN)
    @Override
    public void testService(String s) {
        System.out.println("tes -ervice invoke:"+s);
    }

    @Override
    public ShareDto auditById(Integer id, ShareAuditDto shareAuditDto) {
        //更新审批状态(主流程)
//        Share share = auditByIdInDB(id, shareAuditDto);
        //分享内容通过，添加积分(次流程)
        //异步执行 1、asyncRestTemplate 2、@Async 3、webclient(spring5.0引入) 4、MQ
        if(Objects.equals("PASS",shareAuditDto.getAuditStatusEnum().toString())){
            //发送半消息
            rocketMQTemplate.sendMessageInTransaction(
                    "tx-add-bonus-group",
                    "add-bonus",
                    MessageBuilder
                            .withPayload(UserAddBonusMsgDTO.builder().userId(id).bonus(500).build())
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID())
                            .setHeader("share_id",id)
                            .build(),
                    shareAuditDto
            );

//            userServiceFeignClient.addBonus(id,500);
            //发送消息
//            rocketMQTemplate.convertAndSend(
//                    "add-bonus",
//                    UserAddBonusMsgDTO.builder().userId(id).bonus(500).build());
        }else{
            //REJECT的话不用插消息，只更新状态即可
           auditByIdInDB(id, shareAuditDto);
        }
        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    public Share auditByIdInDB(Integer id,ShareAuditDto shareAuditDto){
        //查询资源是否存在
        Share share = shareMapper.selectById(id);
        if(share == null){
            throw new IllegalArgumentException("illegal param");
        }
        if(!Objects.equals("NOT_YET",share.getAuditStatus())){
            throw new IllegalArgumentException("article already be audited");
        }
        share.setAuditStatus(shareAuditDto.getAuditStatusEnum().toString());
        shareMapper.updateById(share);
        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public ShareDto auditByIdInDBWithRocketMqLog(Integer id,ShareAuditDto shareAuditDto,String transactionId){
        auditByIdInDB(id,shareAuditDto);
        rocketmqTransactionLogMapper.insert(
                RocketmqTransactionLog
                        .builder().transactionId(transactionId).log("audit share content")
                .build()
        );
        //模拟异常，rocketmq rollback tx
//        throw new RuntimeException("test 4 db exception");
        return null;

    }
}
