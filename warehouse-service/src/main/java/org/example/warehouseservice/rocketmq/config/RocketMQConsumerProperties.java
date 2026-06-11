package org.example.warehouseservice.rocketmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQConsumerProperties {
    private String nameServer;
    private Map<String,ConsumerConfig> consumerConfig;

    public static class ConsumerConfig{
        private String group;
        private String topic;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public Map<String, ConsumerConfig> getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(Map<String, ConsumerConfig> consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
