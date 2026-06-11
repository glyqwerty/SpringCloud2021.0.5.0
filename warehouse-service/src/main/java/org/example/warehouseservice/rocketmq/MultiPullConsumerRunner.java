//package org.example.warehouseservice.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class MultiPullConsumerRunner implements CommandLineRunner {
//
//    @Resource(name = "orderPullConsumer")
//    private DefaultLitePullConsumer orderPullConsumer;
//
//    @Resource(name = "logPullConsumer")
//    private DefaultLitePullConsumer logPullConsumer;
//
//    // 使用线程池来运行消费者，避免阻塞主线程
//    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 启动消费者实例
//        orderPullConsumer.start();
//        logPullConsumer.start();
//
//        System.out.println("=== 多个 PullConsumer 启动成功 ===");
//
//        // 提交任务到线程池
//        executorService.submit(this::consumeOrderMessages);
//        executorService.submit(this::consumeLogMessages);
//    }
//
//    private void consumeOrderMessages() {
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                // 拉取消息（阻塞式）
//                List<MessageExt> messages = orderPullConsumer.poll();
//                if (messages != null && !messages.isEmpty()) {
////                    for (MessageExt msg : messages) {
////                        System.out.println("[订单消费者] 收到消息: " + new String(msg.getBody()));
//                        // 业务逻辑...
////                    }
//                    System.out.println("[订单消费者] 收到消息: " + messages.size());
//                    TimeUnit.MILLISECONDS.sleep(100);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                // 生产环境需增加重试或休眠机制，防止死循环报错
//            }
//        }
//    }
//
//    private void consumeLogMessages() {
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                List<MessageExt> messages = logPullConsumer.poll();
//                if (messages != null && !messages.isEmpty()) {
//                    for (MessageExt msg : messages) {
//                        System.out.println("[日志消费者] 收到消息: " + new String(msg.getBody()));
//                        // 业务逻辑...
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}