package org.example.warehouseservice.rocketmq;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "topic2",consumerGroup = "pullgroup1",consumeMode = ConsumeMode.ORDERLY)
public class PushConsumer1  implements RocketMQListener<String> {
    Logger logger = LoggerFactory.getLogger(PushConsumer1.class);
    @Override
    public void onMessage(String s) {
        logger.info("PullConsumer Receive New Messages: {}" , s);
    }
}
