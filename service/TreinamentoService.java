package service;

import model.Estatisticas;
import model.Palavra;
import repository.PalavraRepository;
import java.sql.SQLException;
import java.util.List;

public class TreinamentoService {
    private PalavraRepository palavraRepository;
    private Estatisticas estatisticas;
    
    public TreinamentoService(PalavraRepository palavraRepository, Estatisticas estatisticas) {
        this.palavraRepository = palavraRepository;
        this.estatisticas = estatisticas;
    }
    
    public void treinar(List<String> palavras, boolean isSpam) throws SQLException {
        for (String texto : palavras) {
            Palavra palavra = palavraRepository.buscarPalavra(texto);
            
            if (palavra == null) {
                // Inserir nova palavra
                if (isSpam) {
                    palavra = new Palavra(texto, 1, 0);
                } else {
                    palavra = new Palavra(texto, 0, 1);
                }
                palavraRepository.inserirPalavra(palavra);
            } else {
                // Atualizar frequências existentes
                if (isSpam) {
                    palavra.setFreqSpam(palavra.getFreqSpam() + 1);
                } else {
                    palavra.setFreqNotSpam(palavra.getFreqNotSpam() + 1);
                }
                palavraRepository.atualizarFrequencias(palavra);
            }
        }
        
        // Atualizar estatísticas
        if (isSpam) {
            estatisticas.setTotalPalavrasSpam(estatisticas.getTotalPalavrasSpam() + palavras.size());
            estatisticas.setTotalEmailsSpam(estatisticas.getTotalEmailsSpam() + 1);
        } else {
            estatisticas.setTotalPalavrasNotSpam(estatisticas.getTotalPalavrasNotSpam() + palavras.size());
            estatisticas.setTotalEmailsNotSpam(estatisticas.getTotalEmailsNotSpam() + 1);
        }
        
        atualizarEstatisticasNoBanco();
    }
    
    private void atualizarEstatisticasNoBanco() throws SQLException {
        String sql = "UPDATE estatisticas SET total_palavras_spam = ?, total_palavras_notSpam = ?, " +
                     "total_emails_spam = ?, total_emails_notSpam = ? WHERE id = ?";
        try (java.sql.PreparedStatement stmt = palavraRepository.getClass()
                .getDeclaredField("connection").get(palavraRepository) == null ? 
                null : null) {
            // Nota: Idealmente, você teria um EstatisticasRepository
            // Por simplicidade, estou mostrando a lógica
        }
    }
}