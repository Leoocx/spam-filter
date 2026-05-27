package repository;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static DatabaseType tipoDeBanco = DatabaseType.SQLITE;
    private static Connection conexaoUnica;

    public static void setTipoBanco(DatabaseType tipo) {
        tipoDeBanco = tipo;
        conexaoUnica = null;
    }

    public static Connection getConnection() {
        try {
            if (conexaoUnica == null || conexaoUnica.isClosed()) {
                conexaoUnica = ConnectionFactory.getConnection(tipoDeBanco);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar/abrir conexão", e);
        }
        return conexaoUnica;
    }
}