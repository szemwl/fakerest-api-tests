package commerce.kafka.support;

import commerce.kafka.KafkaSettings;

public class KafkaTestFacade {

    private final KafkaMessageReader messageReader;
    private final KafkaOffsetHelper offsetHelper;

    public KafkaTestFacade(KafkaSettings settings) {
        this.offsetHelper = new KafkaOffsetHelper(settings);
        this.messageReader = new KafkaMessageReader(settings, offsetHelper);
    }

    public String readNewRawMessage(String topic, Runnable action) {
        return messageReader.readNewRawMessage(topic, action);
    }

    public <T> T readNewMessage(String topic, Class<T> clazz, Runnable action) {
        return messageReader.readNewMessage(topic, clazz, action);
    }

    public String createNewGroupAtEnd(String topic) {
        String groupId = offsetHelper.randomGroupId();
        offsetHelper.moveGroupToEnd(topic, groupId);
        return groupId;
    }
}
