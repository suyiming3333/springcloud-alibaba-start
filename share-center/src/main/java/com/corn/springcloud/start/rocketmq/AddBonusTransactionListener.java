package com.corn.springcloud.start.rocketmq;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.corn.springcloud.start.share.dto.ShareAuditDto;
import com.corn.springcloud.start.share.entity.RocketmqTransactionLog;
import com.corn.springcloud.start.share.mapper.RocketmqTransactionLogMapper;
import com.corn.springcloud.start.share.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@Slf4j
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private ShareService shareService;

    @Autowired
    private RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        //获取 share_id
        //get的时候获取到的是字符串
        Integer share_id = Integer.valueOf((String) headers.get("share_id"));
        try {
            //执行本地事务,同时插入rocketmq的事务日志，便于checkLocalTransaction回查
            shareService.auditByIdInDBWithRocketMqLog(share_id, (ShareAuditDto) o,transactionId);
            //本地事务执行成功，提交事务
            log.info("rocketmq commit tx");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            //失败则回滚事务
            log.error("rocketmq rollback tx");
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    /**
     * 生产者定时检查broker中UNKNOW状态的消息,用于补偿，如极端情况下，发送commit的时候断网了
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        Wrapper<RocketmqTransactionLog> wrapper = new Wrapper<RocketmqTransactionLog>() {
            @Override
            public RocketmqTransactionLog getEntity() {
                return RocketmqTransactionLog.builder().transactionId(transactionId).build();
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        };
        //根据事务ID查询事务是否存在
        RocketmqTransactionLog rocketmqTransactionLog = rocketmqTransactionLogMapper.selectOne(wrapper);
        if(rocketmqTransactionLog !=null){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
