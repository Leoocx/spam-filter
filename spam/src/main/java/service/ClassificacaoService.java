package service;

import util.TextProcessor;
import java.sql.SQLException;
import java.util.List;

/*
Essa classe recebe o email bruto e processa o texto usando a classe TextProcessor, em seguida aplica o algoritmo de Naive Bayes para saber se esse email é considerado spam ou não, em seguida determina se deve treinar ou não o modelo(por padrão, deveTreinar é true para ir treinando o modelo automaticamente, isso pode ser alterado na Main).
*/


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