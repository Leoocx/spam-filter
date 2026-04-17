package repository;

import java.beans.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Palavra;

public class PalavraRepository {

    private static Connection connectionDB;

    public static void inserirPalavra(Palavra palavra){
        String sql = "INSERT INTO palavras (text, freqSpam, freqNotSpam) VALUES (?,?,?)";

        try(PreparedStatement stmt = connectionDB.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, palavra.getText());
            stmt.setInt(2, palavra.getFreqSpam());
            stmt.setInt(3, palavra.getFreqNotSpam());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    palavra.setId(rs.getInt(1));
                }
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }


    };
    public static void deletarPalavra(Palavra palavra){
        String sql = "DELETE FROM palavras WHERE id = ?";

        try(PreparedStatement stmt = connectionDB.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, palavra.getId());
            stmt.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }

    };
    public static void atualizarPalavra(Palavra palavra){
        
        String sql = "UPDATE palavras SET text = ?, freqSpam = ?, freqNotSpam = ?, WHERE id = ?";


        try(PreparedStatement stmt = connectionDB.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, palavra.getText());
            stmt.setInt(2, palavra.getFreqSpam());
            stmt.setInt(3, palavra.getFreqNotSpam());
            stmt.setInt(4, palavra.getId());

            stmt.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
    };

    public List<Palavra> exibirPalavras(){
        List<Palavra> palavras = new ArrayList<>();
        String sql = "SELECT * FROM palavras";
        try(PreparedStatement stmt = connectionDB.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Palavra palavra = new Palavra();
                palavra.setId(rs.getInt("id"));
                palavra.setText(rs.getString("text"));
                palavra.setId(rs.getInt("freqSpam"));
                palavra.setId(rs.getInt("freqNotSpam"));
                palavras.add(palavra);
            }
        } catch(SQLException e){
            System.err.println("ERRO AO LISTAR PRODUTOS: "+ e.getMessage());
        }
        return palavras;
    }
}
