package org.example.billservice.demos.rokcketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.example.billservice.demos.mapper.InBillMapper;
import org.example.domain.InBillInfo;
import org.example.domain.enumd.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RocketMQTransactionListener()
public class OrderTransactionListener implements RocketMQLocalTransactionListener {
    @Autowired
    private InBillMapper inBillMapper;

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            byte[] body = (byte[]) msg.getPayload();
            String json = new String(body, StandardCharsets.UTF_8);
            InBillInfo inBillInfo = objectMapper.readValue(json, InBillInfo.class);
            int i = inBillMapper.updateBillStatusByBillCode(inBillInfo.getBillCode(), BillStatus.COMPLETE,BillStatus.EXECUTING);
            if(i>0){
                return RocketMQLocalTransactionState.COMMIT;
            }
            return RocketMQLocalTransactionState.ROLLBACK;
        }catch (Exception e){
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            byte[] body = (byte[]) msg.getPayload();
            String json = new String(body, StandardCharsets.UTF_8);
            InBillInfo inBillInfo = objectMapper.readValue(json, InBillInfo.class);
            if(inBillInfo==null){
                return RocketMQLocalTransactionState.ROLLBACK;
            }
            InBillInfo inBillInfo1 = inBillMapper.selectBillInfoByBillCode(inBillInfo.getBillCode());
            if(inBillInfo1==null){
                return RocketMQLocalTransactionState.ROLLBACK;
            }else{
                if(inBillInfo1.getStatus()==BillStatus.COMPLETE){
                    return  RocketMQLocalTransactionState.COMMIT;
                }
            }
            int i = inBillMapper.updateBillStatusByBillCode(inBillInfo.getBillCode(), BillStatus.COMPLETE,BillStatus.EXECUTING);
            if(i>0){
                return RocketMQLocalTransactionState.COMMIT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
