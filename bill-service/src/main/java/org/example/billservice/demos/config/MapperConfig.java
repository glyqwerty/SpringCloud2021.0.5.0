package org.example.billservice.demos.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.example.billservice.**.mapper")
public class MapperConfig {
}
