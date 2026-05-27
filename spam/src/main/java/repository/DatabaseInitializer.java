package repository;

import java.sql.SQLException;

public class DatabaseInitializer {
    public static void init() throws SQLException {
        try (var conn = ConnectionManager.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS palavras (" +
                    "palavra VARCHAR(100) PRIMARY KEY," +
                    "freq_spam INT DEFAULT 0," +
                    "freq_notSpam INT DEFAULT 0)");
            stmt.execute("CREATE TABLE IF NOT EXISTS estatisticas (" +
                    "id INT PRIMARY KEY," +
                    "total_palavras_spam INT DEFAULT 0," +
                    "total_palavras_notSpam INT DEFAULT 0," +
                    "total_emails_spam INT DEFAULT 0," +
                    "total_emails_notSpam INT DEFAULT 0)");
        }
    }
}