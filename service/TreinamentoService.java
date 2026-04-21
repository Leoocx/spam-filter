package service;

import model.Estatisticas;
import model.Palavra;
import repository.PalavraRepository;
import repository.EstatisticasRepository;
import java.sql.SQLException;
import java.util.List;

public class TreinamentoService {
    private PalavraRepository palavraRepository;
    private EstatisticasRepository estatisticasRepository;
    private Estatisticas estatisticas;
    
    public TreinamentoService(PalavraRepository palavraRepository, 
                              EstatisticasRepository estatisticasRepository,
                              Estatisticas estatisticas) {
        this.palavraRepository = palavraRepository;
        this.estatisticasRepository = estatisticasRepository;
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
        
        // Atualizar estatísticas usando o repository
        if (isSpam) {
            estatisticasRepository.incrementarPalavrasSpam(palavras.size());
            estatisticasRepository.incrementarEmailsSpam();
            
            // Atualizar objeto local
            estatisticas.setTotalPalavrasSpam(estatisticas.getTotalPalavrasSpam() + palavras.size());
            estatisticas.setTotalEmailsSpam(estatisticas.getTotalEmailsSpam() + 1);
        } else {
            estatisticasRepository.incrementarPalavrasNotSpam(palavras.size());
            estatisticasRepository.incrementarEmailsNotSpam();
            
            // Atualizar objeto local
            estatisticas.setTotalPalavrasNotSpam(estatisticas.getTotalPalavrasNotSpam() + palavras.size());
            estatisticas.setTotalEmailsNotSpam(estatisticas.getTotalEmailsNotSpam() + 1);
        }
    }
}
