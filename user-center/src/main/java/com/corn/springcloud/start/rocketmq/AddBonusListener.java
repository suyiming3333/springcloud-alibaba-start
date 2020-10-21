package com.corn.springcloud.start.rocketmq;

import com.corn.springcloud.start.dto.UserAddBonusMsgDTO;
import com.corn.springcloud.start.user.entity.BonusEventLog;
import com.corn.springcloud.start.user.service.BonusEventLogService;
import com.corn.springcloud.start.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumer-group",topic = "add-bonus")
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    @Autowired
    private UserService userService;

    @Autowired
    private BonusEventLogService bonusEventLogService;

    @Override
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        log.info("recived msg:{}",userAddBonusMsgDTO.toString());
        Integer bonus = userAddBonusMsgDTO.getBonus();
        Integer userId = userAddBonusMsgDTO.getUserId();
        //添加用户积分
        userService.addBonus(userId,bonus);
        //添加用户积分事件记录
        BonusEventLog bonusEventLog = new BonusEventLog();
        bonusEventLog.setUserId(userId);
        bonusEventLog.setValue(bonus);
        bonusEventLog.setEvent("test event");
        bonusEventLog.setDescription("test 4");
        bonusEventLog.setCreateTime(LocalDateTime.now());
        bonusEventLogService.save(bonusEventLog);
    }
}
