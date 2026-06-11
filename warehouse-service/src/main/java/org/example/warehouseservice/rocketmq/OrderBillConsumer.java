package org.example.warehouseservice.rocketmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

import org.example.domain.InBillInfo;
import org.example.warehouseservice.service.BMaterialStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(topic = "order_bill_tx",consumerGroup = "ordertxconsumer")
public class OrderBillConsumer implements RocketMQListener<String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BMaterialStockService bMaterialStockService;


    private static final Logger log = LoggerFactory.getLogger(OrderBillConsumer.class);

    @Override
    public void onMessage(String message) {
        try {
            InBillInfo inBillInfo = objectMapper.readValue(message, InBillInfo.class);
            bMaterialStockService.inwarehouseBill(inBillInfo);

        } catch (Exception e) {
            log.error("消费失败",e);
            throw new RuntimeException(e);
        }
    }
}
