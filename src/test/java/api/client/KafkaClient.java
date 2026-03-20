package api.client;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaClient {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final long SEND_TIMEOUT_SECONDS = 10;
    private static final long READ_TIMEOUT_MS = 10_000;
    private static final long POLL_TIMEOUT_MS = 500;

    public static KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false");
        return new KafkaConsumer<>(props);
    }

    public static RecordMetadata sendMessage(KafkaProducer<String, String> producer,
                                             String topic,
                                             String key,
                                             String value) throws Exception {
        return producer.send(new ProducerRecord<>(topic, key, value))
                .get(SEND_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public static ConsumerRecord<String, String> waitForRecord(
            KafkaConsumer<String, String> consumer,
            RecordMetadata metadata) {

        TopicPartition topicPartition = new TopicPartition(
                metadata.topic(),
                metadata.partition()
        );

        consumer.assign(List.of(topicPartition));
        consumer.seek(topicPartition, metadata.offset());

        long endTime = System.currentTimeMillis() + READ_TIMEOUT_MS;

        while (System.currentTimeMillis() < endTime) {
            for (ConsumerRecord<String, String> record :
                    consumer.poll(Duration.ofMillis(POLL_TIMEOUT_MS))) {
                if (record.offset() == metadata.offset()) {
                    return record;
                }
            }
        }

        return null;
    }
}
