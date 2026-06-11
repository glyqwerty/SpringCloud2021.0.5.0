package org.example.billservice.demos.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 只用Caffeine作为缓存
 */
//@Configuration
public class CaffeineConfig {
    @Bean
    public CacheManager cacheManager( ) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList("material","stock"));
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                // 访问后10分钟过期
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats());

        return cacheManager;

    }


}
