package org.example.notificator.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.notificator.util.EmailNotify;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final EmailNotify notifier;

    public KafkaConsumer(EmailNotify notifier) {
        this.notifier = notifier;
    }

    @KafkaListener(topics = "user-notification", groupId = "my_consumer")
    public void listen(ConsumerRecord<String, String> record) {
        notifier.notify(record.value());
    }
}
