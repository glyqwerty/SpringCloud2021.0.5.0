//package org.example.warehouseservice.rocketmq.config;
//
//import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
//import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class PullConsumerConfig {
//    @Resource
//    private RocketMQConsumerProperties rocketMQConsumerProperties;
//
//    @Bean("orderPullConsumer")
//    public DefaultLitePullConsumer orderPullConsumer() throws MQClientException {
//        RocketMQConsumerProperties.ConsumerConfig orderConfig = rocketMQConsumerProperties.getConsumerConfig().get("order");
//        DefaultLitePullConsumer orderConsumer = new DefaultLitePullConsumer(orderConfig.getGroup());
//        orderConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
//        orderConsumer.subscribe(orderConfig.getTopic(),"*");
//        orderConsumer.setPullBatchSize(10);
//        return orderConsumer;
//    }
//
//    @Bean("logPullConsumer")
//    public DefaultLitePullConsumer logPullConsumer() throws MQClientException {
//        RocketMQConsumerProperties .ConsumerConfig logConfig = rocketMQConsumerProperties.getConsumerConfig().get("log");
//        DefaultLitePullConsumer logConsumer = new DefaultLitePullConsumer(logConfig.getGroup());
//        logConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameServer());
//        logConsumer.subscribe(logConfig.getTopic(),"*");
//        logConsumer.setPullBatchSize(10);
//        return logConsumer;
//    }
//}
