package api.tests;

import api.client.KafkaClient;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KafkaSimpleTest {

    private static final String TOPIC = "simple-topic";

    @Test
    @Story("Kafka")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешное отправление и получение данных из Kafka")
    @Description("Создаём producer и consumer отправляем сообщение в Kafka")
    void shouldSendAndReadMessage() throws Exception {
        String expectedKey = UUID.randomUUID().toString();
        String expectedValue = "{\"id\":1,\"name\":\"Vova\"}";

        try (
                KafkaProducer<String, String> producer = KafkaClient.createProducer();
                KafkaConsumer<String, String> consumer = KafkaClient.createConsumer()
        ) {
            RecordMetadata metadata = KafkaClient.sendMessage(
                    producer,
                    TOPIC,
                    expectedKey,
                    expectedValue
            );

            ConsumerRecord<String, String> actualRecord = KafkaClient.waitForRecord(consumer, metadata);

            assertNotNull(actualRecord, "Сообщение не прочиталось из Kafka");
            assertEquals(expectedKey, actualRecord.key());
            assertEquals(expectedValue, actualRecord.value());
        }
    }
}
