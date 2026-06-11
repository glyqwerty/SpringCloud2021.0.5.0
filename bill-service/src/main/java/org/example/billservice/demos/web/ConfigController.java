//package org.example.billservice.demos.web;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/config")
//@RefreshScope
//public class ConfigController {
//
//
//    @Value("${user.name2:default}")
//    private String userName;   // 提供默认值，避免占位符原样输出
//
//    @Value("${user.age}")
//    private String age;
//
//    @RequestMapping("/get")
//    public String get() {
//        return userName;
//    }
//}