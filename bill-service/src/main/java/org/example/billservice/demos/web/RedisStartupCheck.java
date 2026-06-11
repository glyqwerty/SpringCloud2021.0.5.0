package org.example.billservice.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisStartupCheck implements CommandLineRunner {

    @Autowired(required = false) // 防止没有 Redis 依赖时报错
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        if (redisTemplate != null) {
//            redisTemplate.
            try {
                // 尝试 ping 一下
                redisTemplate.opsForValue().set("startup_check", "ok");
                System.out.println("✅ Redis 连接成功！");
            } catch (Exception e) {
                System.err.println("❌ Redis 连接失败，请检查 Redis 服务是否启动: " + e.getMessage());
                // 如果你想强制停止应用，可以取消下面的注释
                // System.exit(1); 
            }
        } else {
            System.out.println("⚠️ 未检测到 RedisTemplate，可能未引入依赖");
        }
    }
}