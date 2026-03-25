package commerce.store;

import commerce.config.AppConfig;
import commerce.config.StorageType;
import commerce.store.memory.InMemoryOrderStore;
import commerce.store.memory.InMemoryProcessedEventStore;
import commerce.store.postgres.DbConnectionFactory;
import commerce.store.postgres.PostgresOrderStore;
import commerce.store.postgres.PostgresProcessedEventStore;
import commerce.store.postgres.SchemaInitializer;

public class StoreFactory {

    private final AppConfig config;
    private final DbConnectionFactory connectionFactory;

    public StoreFactory(AppConfig config) {
        this.config = config;
        this.connectionFactory = new DbConnectionFactory(config);

        if (config.storageType() == StorageType.POSTGRES) {
            new SchemaInitializer(connectionFactory).initialize();
        }
    }

    public OrderStore createOrderStore() {
        if (config.storageType() == StorageType.MEMORY) {
            return new InMemoryOrderStore();
        }

        if (config.storageType() == StorageType.POSTGRES) {
            return new PostgresOrderStore(connectionFactory);
        }

        throw new IllegalArgumentException("Неизвестный storage type: " + config.storageType());
    }

    public ProcessedEventStore createProcessedEventStore() {
        if (config.storageType() == StorageType.MEMORY) {
            return new InMemoryProcessedEventStore();
        }

        if (config.storageType() == StorageType.POSTGRES) {
            return new PostgresProcessedEventStore(connectionFactory);
        }

        throw new IllegalArgumentException("Неизвестный storage type: " + config.storageType());
    }
}
