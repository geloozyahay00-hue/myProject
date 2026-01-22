package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private final String url;

    public Database(String fileName) {
        this.url = "jdbc:sqlite:" + fileName;
    }

    public Connection connect() {
        try {
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            throw new RuntimeException("DB connect error", e);
        }
    }

    public void init() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                username TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL
            );
        """;
        try (Connection c = connect(); Statement st = c.createStatement()) {
            st.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("DB init error", e);
        }
    }
}
