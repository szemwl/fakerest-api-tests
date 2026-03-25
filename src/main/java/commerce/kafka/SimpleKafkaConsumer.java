package commerce.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

public class SimpleKafkaConsumer implements AutoCloseable {

    private final KafkaConsumer<String, String> consumer;

    public SimpleKafkaConsumer(KafkaSettings settings, String groupId, String topic) {
        this.consumer = new KafkaConsumer<>(settings.consumerProperties(groupId));
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    public String pollRawMessage(Duration timeout) {
        long deadline = System.currentTimeMillis() + timeout.toMillis();

        while (System.currentTimeMillis() < deadline) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));

            if (!records.isEmpty()) {
                return records.iterator().next().value();
            }
        }

        throw new RuntimeException("Сообщение не найдено в Kafka за время ожидания: " + timeout);
    }

    @Override
    public void close() {
        consumer.close();
    }
}
