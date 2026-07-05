//package org.example.billservice.demos.rokcketmq;
//
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class BillProducerController   {
//    Logger log = LoggerFactory.getLogger(BillProducerController.class);
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @GetMapping("/sendmessage")
//    public String sendmessage( ) throws Exception {
//        log.info("Send Message");
//        rocketMQTemplate.convertAndSend("topic2", "Hello World");
//        return "Send Message";
//
//    }
//    @GetMapping("/sendBillmessage")
//    public String sendBillmessage( ) throws Exception {
//        log.info("Send Bill Message");
//        int i  =1;
//        while(i==10){
//
//            Message message = new Message();
//            message.setTopic("");
//            rocketMQTemplate.convertAndSend("order-topic", "Hello World");
//            Thread.sleep(100);
//            i++;
//        }
//        return "Send Bill Message";
//
//    }
//}
