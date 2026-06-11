package org.example.billservice.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
public class CacheStatsController {

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/cache/stats")
    public Object getStats() {
        // 获取名为 "users" 的缓存
        org.springframework.cache.Cache usersCache = cacheManager.getCache("users");
        if (usersCache != null) {
            // 获取原生 Caffeine 缓存对象
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = 
                (com.github.benmanes.caffeine.cache.Cache<Object, Object>) usersCache.getNativeCache();
            
            // 获取统计信息
            com.github.benmanes.caffeine.cache.stats.CacheStats stats = nativeCache.stats();

            Map map  = new HashMap();
            map.put("hitRate",stats.hitRate());
            map.put("evictionCount",stats.evictionCount());
            map.put("averageLoadTime",stats.averageLoadPenalty());
            return  map;
        }
        return "Cache not found";
    }
}