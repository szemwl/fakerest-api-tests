package commerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static AppConfig load() {
        Properties properties = new Properties();

        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось загрузить application.properties", ex);
        }

        String storageTypeValue = getValue("storage.type", "STORAGE_TYPE", properties, "memory");
        String dbUrl = getValue("db.url", "DB_URL", properties, "");
        String dbUsername = getValue("db.username", "DB_USERNAME", properties, "");
        String dbPassword = getValue("db.password", "DB_PASSWORD", properties, "");

        return new AppConfig(
                StorageType.valueOf(storageTypeValue.toUpperCase()),
                dbUrl,
                dbUsername,
                dbPassword
        );
    }

    private static String getValue(String propertyKey,
                                   String envKey,
                                   Properties properties,
                                   String defaultValue) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return properties.getProperty(propertyKey, defaultValue);
    }
}
