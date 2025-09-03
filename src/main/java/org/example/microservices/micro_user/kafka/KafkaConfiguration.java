package org.example.microservices.micro_user.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic newTopic() {
        return new NewTopic("user-notification", 2, (short) 1);
    }
}
