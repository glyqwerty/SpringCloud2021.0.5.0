//package org.example.warehouseservice.threadPool;
//
//import io.netty.util.concurrent.RejectedExecutionHandlers;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//
//@Configuration
//@EnableAsync
//public class AsyncExecutorConfig {
//    @Bean("warehouseExecutor")
//    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(16);
//        executor.setMaxPoolSize(32);
//        executor.setQueueCapacity(1000);
//        executor.setKeepAliveSeconds(30);
//        executor.setThreadNamePrefix("warehouse-");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//
//        executor.setAwaitTerminationSeconds(60);
//        executor.initialize();
//        return executor;
//    }
//    @Bean("stockScheduleExecutor")
//    public ThreadPoolTaskScheduler stockExecutor() {
//        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
//        executor.setPoolSize(1);
//        executor.setThreadNamePrefix("stockOperatorExecutor-");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//
//        executor.setAwaitTerminationSeconds(60);
//        executor.initialize();
//        return executor;
//    }
//}
