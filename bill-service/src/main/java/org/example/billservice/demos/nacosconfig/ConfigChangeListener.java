//package org.example.billservice.demos.nacosconfig;
//
//import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConfigChangeListener {
//
//    @EventListener
//    public void onConfigChange(RefreshScopeRefreshedEvent event) {
//        // 当配置刷新时触发
//        System.out.println("🔥 检测到配置刷新！");
//         //event.getSource() //可能包含变更的 keys，具体取决于版本
//
////        System.out.println(event.getSource());
//        // 你可以在这里重新加载某些静态变量或缓存
//    }
//}
