package repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

        private static final String URL_JDBC_PADRAO = "jdbc:sqlite:database.db";

         public static Connection conectar() {           //PARA CONECTAR AO BANCO DE DADOS SQLite

            File dbFile = new File("database.db");
            boolean bancoExiste = dbFile.exists(); // Verifica se o banco já existe

            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(URL_JDBC_PADRAO);

                if (!bancoExiste) {
                    System.out.println("Banco de dados criado!");
                }

                return conn;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver SQLite não encontrado!", e);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao conectar ao banco de dados!", e);
            }
        }


    public static Connection conectarGenerico(String url, String usuario, String senha) {    //PARA OUTROS BANCOS DE DADOS
        try {
                return DriverManager.getConnection(url,usuario,senha);
        } catch (SQLException e) {
            System.err.println("ERRO: "+e.getMessage());
            return null;
          }
        }
}
