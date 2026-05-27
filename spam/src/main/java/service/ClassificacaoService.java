package service;

import util.TextProcessor;
import java.sql.SQLException;
import java.util.List;

public class ClassificacaoService {
    private NaiveBayesService naiveBayesService;
    private TreinamentoService treinamentoService;
    private TextProcessor textProcessor;

    public ClassificacaoService(NaiveBayesService naiveBayesService,
                                TreinamentoService treinamentoService,
                                TextProcessor textProcessor) {
        this.naiveBayesService = naiveBayesService;
        this.treinamentoService = treinamentoService;
        this.textProcessor = textProcessor;
    }

    public boolean processarEmail(String email, boolean deveTreinar) throws SQLException {
        List<String> palavras = textProcessor.processarTexto(email);
        boolean isSpam = naiveBayesService.isSpam(palavras);
        if (deveTreinar) {
            treinamentoService.treinar(palavras, isSpam);
        }
        return isSpam;
    }
}