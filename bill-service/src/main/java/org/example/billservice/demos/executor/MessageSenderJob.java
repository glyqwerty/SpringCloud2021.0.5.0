//package org.example.billservice.demos.executor;
//
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.example.billservice.demos.mapper.BMessageBoxMapper;
//import org.example.domain.BMessageBox;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//@Component
//public class MessageSenderJob {
//    private static final int BATCH_SIZE = 500;
//    private static final Logger log = LoggerFactory.getLogger(MessageSenderJob.class);
//    private final BMessageBoxMapper bMessageBoxMapper;
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    public MessageSenderJob(BMessageBoxMapper bMessageBoxMapper) {
//        this.bMessageBoxMapper = bMessageBoxMapper;
//    }
//
//
//    @Scheduled(fixedRate = 200)
//    public void consumeLoop() {
//        List<BMessageBox> bMessageBoxes = bMessageBoxMapper.noSuccess(BATCH_SIZE);
//        List<String> billCodes = new ArrayList<>(bMessageBoxes.size());
//            if(bMessageBoxes.isEmpty()){
//                return;
//            }
//        for (BMessageBox bMessageBox : bMessageBoxes) {
//            Message<String> build = MessageBuilder.withPayload(bMessageBox.getBody()).build();
//            rocketMQTemplate.syncSend("order_bill_tx",build);
//            billCodes.add(bMessageBox.getBillCode());
//        }
//        if(!bMessageBoxes.isEmpty()) {
//            bMessageBoxMapper.batchUpdateByBillCode(billCodes);
//        }
//
//    }
//}
