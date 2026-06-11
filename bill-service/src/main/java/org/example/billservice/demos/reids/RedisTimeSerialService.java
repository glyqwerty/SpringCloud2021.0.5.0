package org.example.billservice.demos.reids;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
@Service
public class RedisTimeSerialService {
    private static  final  String TIME_SERIES_KEY = "my_time_series";
    private static final String TIME_SERIES_CREATE = "redis.call('TS.CREATE',KEYS[1])";
    private static final String TIME_SERIES_ADD = "TS.ADD "+TIME_SERIES_KEY+" 1 10.8 RETENTION 100" ;
    @Autowired
    private RedissonClient redissonClient;

//    @PostConstruct
//    public void init(){
//        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
//        script.eval(RScript.Mode.READ_WRITE,TIME_SERIES_CREATE,RScript.ReturnType.VALUE,Collections.singletonList(TIME_SERIES_KEY));
//        System.out.println("使用 Redisson 初始化 Time Series 结构完成");
//
//    }

    public void add(String key, Object value){
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);
        script.eval(
                RScript.Mode.READ_WRITE,
                TIME_SERIES_ADD,
                RScript.ReturnType.STATUS
        );
    }

}
