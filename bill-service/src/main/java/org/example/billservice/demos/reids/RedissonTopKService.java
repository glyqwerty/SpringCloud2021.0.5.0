package org.example.billservice.demos.reids;

import org.redisson.api.*;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RedissonTopKService {

    private static final String TOP_K_KEY = "my_topk_list";
    private static final int K = 10; // 保留前10个热门元素
    private static final String FUNCTION_LIB_NAME = "mylib";
    private static final Logger log = LoggerFactory.getLogger(RedissonTopKService.class);
    @Autowired
    private RedissonClient redissonClient;

    // --- Lua 脚本定义 ---

    // 1. 初始化/创建 Top-K 结构: TOPK.RESERVE key k width depth decay
    private static final String RESERVE_SCRIPT =
            "return redis.call('TOPK.RESERVE', KEYS[1], ARGV[1], ARGV[2], ARGV[3], ARGV[4])";

    // 2. 增加元素计数: TOPK.ADD key item [item ...]
    private static final String ADD_SCRIPT =
            "return redis.call('TOPK.ADD', KEYS[1], unpack(ARGV))";

    // 3. 获取 Top-K 列表: TOPK.LIST key
    private static final String LIST_SCRIPT =
            "return redis.call('TOPK.LIST', KEYS[1])";

    // 4. 查询元素是否在 Top-K 中: TOPK.QUERY key item [item ...]
    private static final String QUERY_SCRIPT =
            "return redis.call('TOPK.QUERY', KEYS[1], unpack(ARGV))";
    
    // 5. 获取元素估计频次: TOPK.COUNT key item [item ...]
    private static final String COUNT_SCRIPT =
            "return redis.call('TOPK.COUNT', KEYS[1], unpack(ARGV))";

    // 定义 Lua 函数库的字符串
    private static final String FUNCTION_LIB_CODE =
                    "redis.register_function('knockknock', function() return 'Whos there?' end,flags={ 'no-writes' })";

    private static final String FUNCTION_INC =
                    "redis.register_function('incrby', function(keys,args) " +
                            "local key=keys[1]" +
                            "local incN=tonumber(args[1])" +
                            "return redis.call('INCRBY',key,incN)" +
                            "end)";
    /**
     * 应用启动时，初始化 Top-K 结构
     */
//    @PostConstruct
//    public void init() {
//        try {
//            RFunction function = redissonClient.getFunction();
//            function.loadAndReplace(FUNCTION_LIB_NAME, FUNCTION_LIB_CODE);
//            function.loadAndReplace(FUNCTION_LIB_NAME, FUNCTION_INC);
//            System.out.println("✅ Redis Function 库 '" + FUNCTION_LIB_NAME + "' 加载成功！");

//            RScript script = redissonClient.getScript(StringCodec.INSTANCE);
            // 执行 RESERVE 脚本，参数依次为：k, width, depth, decay
//            script.evalSha(
//                    RScript.Mode.READ_WRITE,
//                    RESERVE_SCRIPT,
//                    RScript.ReturnType.VALUE,
//                    Collections.singletonList(TOP_K_KEY),
//                    K, 20, 7, 0.9
//            );
//            System.out.println("使用 Redisson 初始化 Top-K 结构完成");
//        }catch (Exception e){
//
//            System.out.println("❌ 已经存在对应的脚本");
//        }
//    }

    /**
     * 添加或增加元素的计数
     */
    public void addItem(String... items) {
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
//        List<String> keys = Collections.singletonList(TOP_K_KEY);
        script.eval(
                RScript.Mode.READ_WRITE,
                ADD_SCRIPT,
                // 这里写LIST和STRING目前都可以
                RScript.ReturnType.MULTI,
                Collections.singletonList(TOP_K_KEY),
                (String[]) items
        );
    }

    /**
     * 获取当前的 Top-K 列表
     */
    public List<String> getTopKList() {
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
//        List<String> keys = Collections.singletonList(TOP_K_KEY);
        return script.<List<String>>eval(
                RScript.Mode.READ_WRITE,
                LIST_SCRIPT,
                RScript.ReturnType.MULTI,
                Collections.singletonList(TOP_K_KEY)
        );
    }

    /**
     * 查询某个元素是否在 Top-K 列表中
     */
    public boolean isItemInTopK(String item) {
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
//        List<String> keys = Collections.singletonList(TOP_K_KEY);
        List<Boolean> results = script.eval(
                RScript.Mode.READ_WRITE,
                QUERY_SCRIPT,
                RScript.ReturnType.MULTI,
                Collections.singletonList(TOP_K_KEY),
                item
        );
        return results != null && !results.isEmpty() && results.get(0);
    }
    
    /**
     * 获取元素的估计频次
     */
    public Long getItemCount(String item) {
        RScript script = redissonClient.getScript(LongCodec.INSTANCE);
//        List<String> keys = Collections.singletonList(TOP_K_KEY);
        List<Long> counts = script.eval(
                RScript.Mode.READ_WRITE,
                COUNT_SCRIPT,
                RScript.ReturnType.MULTI,
                Collections.singletonList(TOP_K_KEY),
                item
        );
        return counts != null && !counts.isEmpty() ? counts.get(0) : 0L;
    }
    public String functionDemo(String key, Long maxValue) {
        // 1. 获取 RFunction 实例，并指定返回类型为 Long
        RFunction function = redissonClient.getFunction(LongCodec.INSTANCE);

        return function.call(
                FunctionMode.WRITE,
                 "knockknock",
                FunctionResult.STRING
        );
    }
    public Long incrementWithLimit(String key, Long maxValue) {
        // 1. 获取 RFunction 实例，并指定返回类型为 Long
        RFunction function = redissonClient.getFunction(LongCodec.INSTANCE);

        return function.call(
                FunctionMode.WRITE,
                 "incrby",
                FunctionResult.LONG,
                Collections.singletonList(key),
                maxValue
        );
    }
}