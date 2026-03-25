package commerce.kafka.tests;

import commerce.config.AppConfig;
import commerce.config.ConfigLoader;
import commerce.store.OrderStore;
import commerce.store.ProcessedEventStore;
import commerce.store.StoreFactory;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected OrderStore orderStore;
    protected ProcessedEventStore processedEventStore;

    @BeforeEach
    void setUpStores() {
        AppConfig config = ConfigLoader.load();
        StoreFactory storeFactory = new StoreFactory(config);

        orderStore = storeFactory.createOrderStore();
        processedEventStore = storeFactory.createProcessedEventStore();
    }
}
