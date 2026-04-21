package repository;

import model.Estatisticas;
import java.sql.*;

public class EstatisticasRepository {
    private Connection connection;
    
    public EstatisticasRepository(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Busca as estatísticas do sistema (sempre id = 1)
     */
    public Estatisticas buscarEstatisticas() throws SQLException {
        String sql = "SELECT * FROM estatisticas WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Estatisticas(
                    rs.getInt("id"),
                    rs.getInt("total_palavras_spam"),
                    rs.getInt("total_palavras_notSpam"),
                    rs.getInt("total_emails_spam"),
                    rs.getInt("total_emails_notSpam")
                );
            }
        }
        return null;
    }
    
    /**
     * Insere as estatísticas iniciais (primeira execução)
     */
    public void inserirEstatisticas(Estatisticas estatisticas) throws SQLException {
        String sql = "INSERT INTO estatisticas (id, total_palavras_spam, total_palavras_notSpam, " +
                     "total_emails_spam, total_emails_notSpam) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estatisticas.getId());
            stmt.setInt(2, estatisticas.getTotalPalavrasSpam());
            stmt.setInt(3, estatisticas.getTotalPalavrasNotSpam());
            stmt.setInt(4, estatisticas.getTotalEmailsSpam());
            stmt.setInt(5, estatisticas.getTotalEmailsNotSpam());
            stmt.executeUpdate();
        }
    }
    
    /**
     * Atualiza todas as estatísticas
     */
    public void atualizarEstatisticas(Estatisticas estatisticas) throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_spam = ?, total_palavras_notSpam = ?, " +
                     "total_emails_spam = ?, total_emails_notSpam = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estatisticas.getTotalPalavrasSpam());
            stmt.setInt(2, estatisticas.getTotalPalavrasNotSpam());
            stmt.setInt(3, estatisticas.getTotalEmailsSpam());
            stmt.setInt(4, estatisticas.getTotalEmailsNotSpam());
            stmt.setInt(5, estatisticas.getId());
            stmt.executeUpdate();
        }
    }
    
    /**
     * Atualiza apenas os totais de palavras (spam e notSpam)
     */
    public void atualizarTotaisPalavras(int totalPalavrasSpam, int totalPalavrasNotSpam) throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_spam = ?, total_palavras_notSpam = ? WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, totalPalavrasSpam);
            stmt.setInt(2, totalPalavrasNotSpam);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Atualiza apenas os totais de emails (spam e notSpam)
     */
    public void atualizarTotaisEmails(int totalEmailsSpam, int totalEmailsNotSpam) throws SQLException {
        String sql = "UPDATE estatisticas SET total_emails_spam = ?, total_emails_notSpam = ? WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, totalEmailsSpam);
            stmt.setInt(2, totalEmailsNotSpam);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Incrementa o total de palavras spam
     */
    public void incrementarPalavrasSpam(int quantidade) throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_spam = total_palavras_spam + ? WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Incrementa o total de palavras notSpam
     */
    public void incrementarPalavrasNotSpam(int quantidade) throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_notSpam = total_palavras_notSpam + ? WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Incrementa o total de emails spam
     */
    public void incrementarEmailsSpam() throws SQLException {
        String sql = "UPDATE estatisticas SET total_emails_spam = total_emails_spam + 1 WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
    
    /**
     * Incrementa o total de emails notSpam
     */
    public void incrementarEmailsNotSpam() throws SQLException {
        String sql = "UPDATE estatisticas SET total_emails_notSpam = total_emails_notSpam + 1 WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
    
    /**
     * Verifica se as estatísticas já existem no banco
     */
    public boolean existeEstatisticas() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM estatisticas WHERE id = 1";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        }
        return false;
    }
    
    /**
     * Reseta todas as estatísticas para zero
     */
    public void resetarEstatisticas() throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_spam = 0, total_palavras_notSpam = 0, " +
                     "total_emails_spam = 0, total_emails_notSpam = 0 WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
    
    /**
     * Obtém o total de emails processados (spam + notSpam)
     */
    public int getTotalEmails() throws SQLException {
        String sql = "SELECT (total_emails_spam + total_emails_notSpam) as total FROM estatisticas WHERE id = 1";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * Obtém o total de palavras processadas (spam + notSpam)
     */
    public int getTotalPalavras() throws SQLException {
        String sql = "SELECT (total_palavras_spam + total_palavras_notSpam) as total FROM estatisticas WHERE id = 1";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
