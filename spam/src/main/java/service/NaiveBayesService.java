package service;

import model.Estatisticas;
import model.Palavra;
import repository.PalavraRepository;
import java.sql.SQLException;
import java.util.List;

public class NaiveBayesService {
    private PalavraRepository palavraRepository;
    private Estatisticas estatisticas;

    // Construtor padrão: usa SQLite automaticamente
    public NaiveBayesService(Estatisticas estatisticas) {
        this.palavraRepository = new PalavraRepository();
        this.estatisticas = estatisticas;
    }

    /**
     * Decide se uma lista de palavras (email tokenizado) é spam ou não.
     * Naive Bayes com log e Laplace smoothing.
     */
    public boolean isSpam(List<String> palavras) throws SQLException {
        // Probabilidades a priori (já em log)
        double logProbSpam = Math.log(calcularPriorSpam());
        double logProbNotSpam = Math.log(calcularPriorNotSpam());

        int vocabSize = palavraRepository.getVocabSize();               // total de palavras únicas
        int totalPalavrasSpam = estatisticas.getTotalPalavrasSpam();
        int totalPalavrasNotSpam = estatisticas.getTotalPalavrasNotSpam();

        for (String texto : palavras) {
            Palavra p = palavraRepository.buscarPalavra(texto);
            int freqSpam = (p != null) ? p.getFreqSpam() : 0;
            int freqNotSpam = (p != null) ? p.getFreqNotSpam() : 0;

            // Laplace smoothing: (frequencia + 1) / (totalPalavrasClasse + vocabSize)
            double probSpam = (freqSpam + 1.0) / (totalPalavrasSpam + vocabSize);
            double probNotSpam = (freqNotSpam + 1.0) / (totalPalavrasNotSpam + vocabSize);

            logProbSpam += Math.log(probSpam);
            logProbNotSpam += Math.log(probNotSpam);
        }

        // Quem tiver maior probabilidade logarítmica vence
        return logProbSpam > logProbNotSpam;
    }

    // P(Spam) = emails spam / total de emails
    private double calcularPriorSpam() {
        int total = estatisticas.getTotalEmailsSpam() + estatisticas.getTotalEmailsNotSpam();
        if (total == 0) return 0.5; // nenhum email treinado ainda
        return (double) estatisticas.getTotalEmailsSpam() / total;
    }

    // P(~Spam)
    private double calcularPriorNotSpam() {
        int total = estatisticas.getTotalEmailsSpam() + estatisticas.getTotalEmailsNotSpam();
        if (total == 0) return 0.5;
        return (double) estatisticas.getTotalEmailsNotSpam() / total;
    }
}


/*
*  // Construtor pra testes ou injeção de dependência
    public NaiveBayesService(PalavraRepository palavraRepository, Estatisticas estatisticas) {
        this.palavraRepository = palavraRepository;
        this.estatisticas = estatisticas;
    }
    *
    * A probabilidade de um e-mail ser spam S, dado que uma determinada palavra W aparece,
é definida pelo lado esquerdo da equação acima, Pr(S|W) .

O lado direito da equação apresenta a fórmula para calcular essa probabilidade. É ela:

    a probabilidade de a palavra ocorrer no e-mail, dado que se trata de um e-mail de spam,
    Pr(W|S) multiplicada pela probabilidade de um e-mail ser spam, Pr(S) .
    dividimos a probabilidade de a palavra ocorrer no e-mail, dado que se trata de um e-mail de spam,
    pelo resultado da multiplicação pela probabilidade de um e-mail ser spam.
    mais a probabilidade de a palavra ocorrer no e-mail, dado que é um e-mail não spam Pr(W|¬S)
    multiplicada pela probabilidade de um e-mail não ser spam Pr(¬S) .
* */