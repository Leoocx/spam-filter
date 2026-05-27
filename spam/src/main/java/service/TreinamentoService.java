package service;

import model.Palavra;
import model.Estatisticas;
import repository.PalavraRepository;
import repository.EstatisticasRepository;
import java.sql.SQLException;
import java.util.List;
/**
 * Essa classe é quem ensina o filtro de spam.
 * 
 * Toda vez que um email é classificado (ou quando a gente treina manualmente),
 * ela atualiza o modelo: 
 * - Se a palavra é nova, insere no banco com contagem 1.
 * - Se já existe, aumenta a contagem dela (freqSpam ou freqNotSpam).
 * - Também mantém os totais gerais de palavras e emails por classe.
 * 
 * Basicamente, é aqui que o sistema "aprende" com os exemplos que estão no arquivo csv ou quando se insere manualmente.
 */

public class TreinamentoService {
    private PalavraRepository palavraRepository;
    private EstatisticasRepository estatisticasRepository;
    private Estatisticas estatisticas;

    public TreinamentoService(Estatisticas estatisticas) {
        this.palavraRepository = new PalavraRepository();
        this.estatisticasRepository = new EstatisticasRepository();
        this.estatisticas = estatisticas;
    }
    public TreinamentoService(PalavraRepository palavraRepo, EstatisticasRepository estRepo, Estatisticas estatisticas) {
        this.palavraRepository = palavraRepo;
        this.estatisticasRepository = estRepo;
        this.estatisticas = estatisticas;
    }

    public void treinar(List<String> palavras, boolean isSpam) throws SQLException {
        for (String texto : palavras) {
            Palavra palavra = palavraRepository.buscarPalavra(texto);
            if (palavra == null) {
                palavra = new Palavra(texto, isSpam ? 1 : 0, isSpam ? 0 : 1);
                palavraRepository.inserirPalavra(palavra);
            } else {
                if (isSpam) palavra.setFreqSpam(palavra.getFreqSpam() + 1);
                else palavra.setFreqNotSpam(palavra.getFreqNotSpam() + 1);
                palavraRepository.atualizarFrequencias(palavra);
            }
        }

        if (isSpam) {
            estatisticasRepository.incrementarPalavrasSpam(palavras.size());
            estatisticasRepository.incrementarEmailsSpam();
            estatisticas.setTotalPalavrasSpam(estatisticas.getTotalPalavrasSpam() + palavras.size());
            estatisticas.setTotalEmailsSpam(estatisticas.getTotalEmailsSpam() + 1);
        } else {
            estatisticasRepository.incrementarPalavrasNotSpam(palavras.size());
            estatisticasRepository.incrementarEmailsNotSpam();
            estatisticas.setTotalPalavrasNotSpam(estatisticas.getTotalPalavrasNotSpam() + palavras.size());
            estatisticas.setTotalEmailsNotSpam(estatisticas.getTotalEmailsNotSpam() + 1);
        }
    }
}