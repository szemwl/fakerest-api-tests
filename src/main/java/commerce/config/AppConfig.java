package commerce.config;

public record AppConfig(
        StorageType storageType,
        String dbUrl,
        String dbUsername,
        String dbPassword
) {
}
