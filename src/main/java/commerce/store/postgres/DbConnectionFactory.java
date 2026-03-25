package commerce.store.postgres;

import commerce.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    private final String url;
    private final String username;
    private final String password;

    public DbConnectionFactory(AppConfig config) {
        this.url = config.dbUrl();
        this.username = config.dbUsername();
        this.password = config.dbPassword();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
