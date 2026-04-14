//package org.example.billservice.demos.nacosconfig;
//
//import com.alibaba.cloud.nacos.annotation.NacosConfigListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NacosSpecificListener {
//
//    // 监听 bill-server.yaml 的变更
//    @NacosConfigListener(dataId = "bill-server.yaml", group = "DEFAULT_GROUP")
//    public void onConfigChange(String configContent) {
//        System.out.println("🔥 Nacos 配置发生变更，新内容为: " + configContent);
//        // 可以在这里处理具体的业务逻辑
//    }
//}
