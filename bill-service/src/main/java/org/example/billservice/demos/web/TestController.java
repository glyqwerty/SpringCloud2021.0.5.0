package org.example.billservice.demos.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 必须加这个注解，否则配置不会动态更新
public class TestController {

    @Value("${document:user.name}")
    private String userName;
    @Value("${document:user.age}")
    private String age;

    @GetMapping("/getDocument")
    public String getDocument() {
        return "当前配置值: " + userName;
    }
}
