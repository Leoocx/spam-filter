package repository;

import java.sql.Connection;

public class ConnectionManager {

    private static DatabaseType tipoDeBanco = DatabaseType.SQLITE; // padrão
    private static Connection conexaoUnica;

    public static void setTipoBanco(DatabaseType tipo) {
        tipoDeBanco = tipo;
        conexaoUnica = null; // força reabrir se já existia
    }

    public static Connection getConnection() {
        if (conexaoUnica == null) {
            conexaoUnica = ConnectionFactory.getConnection(tipoDeBanco);
        }
        return conexaoUnica;
    }
}