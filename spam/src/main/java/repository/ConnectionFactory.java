package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public static Connection getConnection(DatabaseType tipo) {
        try {
            switch (tipo) {
                case SQLITE:
                    Class.forName("org.sqlite.JDBC");
                    return DriverManager.getConnection("jdbc:sqlite:database.db");
                case MYSQL:
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    return DriverManager.getConnection("jdbc:mysql://localhost:3306/nomebanco", "usuario", "senha");
                case POSTGRESQL:
                    Class.forName("org.postgresql.Driver");
                    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/nomebanco", "usuario", "senha");
                default:
                    throw new UnsupportedOperationException("Banco não suportado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar: " + e.getMessage(), e);
        }
    }
}