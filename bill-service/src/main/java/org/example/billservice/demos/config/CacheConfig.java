package org.example.billservice.demos.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 与CaffeineConfig类不同的是，这个类是组合CaffeineConfig与Redis组合来实现缓存
 *
 */
@Configuration
public class CacheConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(100)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats());
        return caffeineCacheManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    @Primary
    public CacheManager compositeCacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager) {
        return new CacheManager() {
            @Override
            public Cache getCache(String name) {
                Cache caffeineCache = caffeineCacheManager.getCache(name);
                Cache redisCache = redisCacheManager.getCache(name);
                return new Cache() {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public Object getNativeCache() {
                        return null;
                    }

                    @Override
                    public ValueWrapper get(Object key) {
                        log.info("自定义Cache ... ");
                        // 读取时依然可以先查 Caffeine，再查 Redis
                        ValueWrapper value = caffeineCache.get(key);
                        if (value != null) {
                            return value;
                        }
                        return redisCache.get(key);
                    }

                    @Override
                    public <T> T get(Object key, Class<T> type) {
                        // 读取时依然可以先查 Caffeine，再查 Redis
                        T value = caffeineCache.get(key,type);
                        if (value != null) {
                            return value;
                        }
                        return redisCache.get(key,type);
                    }

                    @Override
                    public <T> T get(Object key, Callable<T> valueLoader) {
                        return null;
                    }

                    @Override
                    public void put(Object key, Object value) {
                        caffeineCache.put(key, value);
                        redisCache.put(key, value);
                    }

                    @Override
                    public void evict(Object key) {
                        caffeineCache.evict(key);
                        redisCache.evict(key);
                    }

                    @Override
                    public void clear() {
                        caffeineCache.clear();
                        redisCache.clear();
                    }
                };
            }

            @Override
            public Collection<String> getCacheNames() {
                return Arrays.asList("material");
            }
        };
    }
}
