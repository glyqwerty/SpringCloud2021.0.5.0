package org.example.billservice.demos.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.example.billservice.demos.mapper.BMessageBoxMapper;
import org.example.billservice.demos.mapper.InBillDetailMapper;
import org.example.billservice.demos.mapper.InBillMapper;
import org.example.billservice.demos.service.BillService;
import org.example.domain.BMessageBox;
import org.example.domain.InBillInfo;
import org.example.domain.enumd.BillStatus;
import org.example.service.DubboBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
public class BillServiceImpl implements DubboBillService,BillService  {
    @Autowired
    private InBillMapper inBillMapper;
    @Autowired
    private InBillDetailMapper inBillDetailMapper;
    @Autowired
    private BMessageBoxMapper bMessageBoxMapper;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public InBillInfo selectBillInfoByBillCode(String billCode) {
        return inBillMapper.selectBillInfoByBillCode(billCode);
    }

    @Override
    @Transactional
    public int insertBill(InBillInfo inBillInfo) {
        // 分布式ID
//        inBillInfo.setBillCode();
        if(inBillInfo.getDetailInfoList()==null || inBillInfo.getDetailInfoList().size()==0){
            return 0;
        }
//        inBillInfo.setCreateTime(LocalDateTime.now());
        inBillInfo.setCreateUser("admin");
        inBillMapper.inserInBillInfo(inBillInfo);
        inBillDetailMapper.inserInBillDetailList(inBillInfo.getDetailInfoList());
        return 1;
    }

    @Override
    public int updateBill(InBillInfo inBillInfo) {
        return 1;
    }

    @Override
    public int deleteBill(String billCode) {
        return 1;
    }

    @Override
    public int executeBill(String billCode) {
        return inBillMapper.updateBillStatusByBillCode(billCode,BillStatus.EXECUTING,BillStatus.NEW);

    }

    @Override
    @Transactional
    public int completeBill(String billCode) throws JsonProcessingException {
        // 修改入库单的状态改成已完成，并且发送RocketMQ消息
        InBillInfo billInfo = getBillInfoByBillCode(billCode);
        if(billInfo==null){
            throw new IllegalArgumentException("不存在单号 " + billCode);
        }
        if(billInfo.getStatus().getCode()!=1){
            return 0;
        }

        String json = objectMapper.writeValueAsString(billInfo);


        Message<String> build = MessageBuilder.withPayload(json).build();
//        Message<InBillInfo> build = MessageBuilder.withPayload(billInfo).build();
        //arg额外参数，会传给本地事务
        rocketMQTemplate.getProducer().setSendMsgTimeout(5000);
        rocketMQTemplate.sendMessageInTransaction("order_bill_tx",build,null);

        return 1;
    }
    @Override
    @Transactional
    public int completeBillTran(String billCode) throws JsonProcessingException {
        // 修改入库单的状态改成已完成，并且发送RocketMQ消息
        InBillInfo billInfo = getBillInfoByBillCode(billCode);
        if(billInfo==null){
            throw new IllegalArgumentException("不存在单号 " + billCode);
        }
        if(billInfo.getStatus()!=BillStatus.EXECUTING){
            return 0;
        }
        int i = inBillMapper.updateBillStatusByBillCode(billCode, BillStatus.COMPLETE,BillStatus.EXECUTING);
        if(i==0){
            return 0;
        }
        String json = objectMapper.writeValueAsString(billInfo);

        BMessageBox bMessageBox = new BMessageBox();
        bMessageBox.setBillCode(billCode);
        bMessageBox.setBody(json);
        bMessageBoxMapper.inserBMessageBox(bMessageBox);

//        Message<String> build = MessageBuilder.withPayload(json).build();
//        Message<InBillInfo> build = MessageBuilder.withPayload(billInfo).build();
        //arg额外参数，会传给本地事务
//        rocketMQTemplate.getProducer().setSendMsgTimeout(5000);
//        rocketMQTemplate.sendMessageInTransaction("order_bill_tx",build,null);

        return 1;
    }

    @Override
    public int inwarehouseBill(String billCode) {

        return inBillMapper.updateBillStatusByBillCode(billCode, BillStatus.INSTACK,BillStatus.COMPLETE);
    }

    @Override
    public org.example.domain.InBillInfo getBillInfoByBillCode(String billCode) {
        InBillInfo inBillInfo = inBillMapper.selectBillInfoByBillCode(billCode);
        inBillInfo.setDetailInfoList(inBillDetailMapper.listBillDetailByBillCode(billCode));
        return inBillInfo;
    }
}
