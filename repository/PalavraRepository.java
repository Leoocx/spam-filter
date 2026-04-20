package repository;

import model.Palavra;
import java.sql.*;
import java.util.*;

public class PalavraRepository {
    private Connection connection;
    
    public PalavraRepository(Connection connection) {
        this.connection = connection;
    }
    
    public Palavra buscarPalavra(String texto) throws SQLException {
        String sql = "SELECT * FROM palavras WHERE palavra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, texto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Palavra(
                    rs.getString("palavra"),
                    rs.getInt("freq_spam"),
                    rs.getInt("freq_notSpam")
                );
            }
        }
        return null;
    }
    
    public void inserirPalavra(Palavra palavra) throws SQLException {
        String sql = "INSERT INTO palavras (palavra, freq_spam, freq_notSpam) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, palavra.getTexto());
            stmt.setInt(2, palavra.getFreqSpam());
            stmt.setInt(3, palavra.getFreqNotSpam());
            stmt.executeUpdate();
        }
    }
    
    public void atualizarFrequencias(Palavra palavra) throws SQLException {
        String sql = "UPDATE palavras SET freq_spam = ?, freq_notSpam = ? WHERE palavra = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, palavra.getFreqSpam());
            stmt.setInt(2, palavra.getFreqNotSpam());
            stmt.setString(3, palavra.getTexto());
            stmt.executeUpdate();
        }
    }
    
    public List<Palavra> listarTodasPalavras() throws SQLException {
        List<Palavra> palavras = new ArrayList<>();
        String sql = "SELECT * FROM palavras";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                palavras.add(new Palavra(
                    rs.getString("palavra"),
                    rs.getInt("freq_spam"),
                    rs.getInt("freq_notSpam")
                ));
            }
        }
        return palavras;
    }
    
    public int getVocabSize() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM palavras";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}