package service;

import model.Estatisticas;
import model.Palavra;
import repository.PalavraRepository;
import java.sql.SQLException;
import java.util.List;


/**Multinomial Naive Bayes (Uma das variações de Naive Bayes)
 * https://www.geeksforgeeks.org/machine-learning/multinomial-naive-bayes/
 * 
 * Essa classe é quem decide se um email é spam ou não.
 *
 * Ela pega as palavras do email já processadas e calcula as chances de ser spam
 *
 *
 * --- por que utilizar os logs? ---
 * Multiplicar muitas probabilidades (tipo 0.05 * 0.03 * 0.01...) dá números
 * extremamente pequenos, que o computador acaba arredondando para zero, ou seja, underflow.
 * Pra evitar, a gente soma os logaritmos dessas probabilidades. Como log(a*b) = log(a)+log(b),
 * no final comparar a soma dos logs é o mesmo que comparar os produtos originais. Ou seja, ao invés de multiplicarmos as probabilidades, as transformamos em uma soma de logs.
 *
 * --- Laplace smoothing ---
 * Se uma palavra nunca apareceu em spam (freqSpam = 0), a probabilidade dela seria zero
 * e mataria a multiplicação toda (qualquer palavra desconhecida tornaria o email "não spam").
 * O smoothing resolve isso: a gente soma 1 no numerador (freq + 1) e soma o tamanho do
 * vocabulário no denominador (totalPalavrasClasse + vocabSize). Assim, palavras novas
 * têm uma chance pequena mas não zero, e palavras com alta frequência continuam relevantes.
 *
 * No final, compara as somas dos logs: se log(Spam) > log(Não Spam), o email é SPAM.
 */
public class NaiveBayesService {
    private PalavraRepository palavraRepository;
    private Estatisticas estatisticas;

    public NaiveBayesService(Estatisticas estatisticas) {
        this.palavraRepository = new PalavraRepository();
        this.estatisticas = estatisticas;
    }

    /**
     * Decide se uma lista de palavras (email já processado) é spam ou não.
     * Naive Bayes com log e Laplace smoothing.
     */
    public boolean isSpam(List<String> palavras) throws SQLException {
        System.out.println("Palavras do email: " + palavras);

        // Probabilidades a priori
        double priorSpam = calcularPriorSpam();
        double priorNotSpam = calcularPriorNotSpam();
        double logProbSpam = Math.log(priorSpam);
        double logProbNotSpam = Math.log(priorNotSpam);

        System.out.printf(" PRIORI: P(Spam)=%.4f (log=%.4f) | P(NãoSpam)=%.4f (log=%.4f)%n",
                priorSpam, logProbSpam, priorNotSpam, logProbNotSpam);

        int vocabSize = palavraRepository.getVocabSize();
        int totalPalavrasSpam = estatisticas.getTotalPalavrasSpam();
        int totalPalavrasNotSpam = estatisticas.getTotalPalavrasNotSpam();

        System.out.printf("Estatísticas: vocabSize=%d, totalPalavrasSpam=%d, totalPalavrasNotSpam=%d%n",
                vocabSize, totalPalavrasSpam, totalPalavrasNotSpam);

        for (String texto : palavras) {
            Palavra p = palavraRepository.buscarPalavra(texto);
            int freqSpam = (p != null) ? p.getFreqSpam() : 0;
            int freqNotSpam = (p != null) ? p.getFreqNotSpam() : 0;

            System.out.printf("\n  Palavra: '%s'  (freqSpam=%d, freqNotSpam=%d)%n", texto, freqSpam, freqNotSpam);

            // Laplace smoothing: (frequencia + 1) / (totalPalavrasX + vocabSize)
            double probSpam = (freqSpam + 1.0) / (totalPalavrasSpam + vocabSize);
            double probNotSpam = (freqNotSpam + 1.0) / (totalPalavrasNotSpam + vocabSize);

            double logProbSpamPalavra = Math.log(probSpam);
            double logProbNotSpamPalavra = Math.log(probNotSpam);

            System.out.printf("    P(palavra|Spam) = (freqSpam+1)/(totalSpam+vocab) = %.4f → log = %.4f%n", probSpam, logProbSpamPalavra);
            System.out.printf("    P(palavra|NaoSpam) = (freqNotSpam+1)/(totalNaoSpam+vocab) = %.4f → log = %.4f%n", probNotSpam, logProbNotSpamPalavra);

            logProbSpam += logProbSpamPalavra;
            logProbNotSpam += logProbNotSpamPalavra;

            System.out.printf(" Soma acumulada: logSpam = %.4f | logNaoSpam = %.4f%n", logProbSpam, logProbNotSpam);
        }

        System.out.printf("\n RESULTADO FINAL: log(Spam)=%.4f, log(NãoSpam)=%.4f%n", logProbSpam, logProbNotSpam);
        boolean resultado = logProbSpam > logProbNotSpam;
        System.out.println((resultado ? "SPAM" : "NÃO SPAM"));
        return resultado;
    }

    private double calcularPriorSpam() {
        int total = estatisticas.getTotalEmailsSpam() + estatisticas.getTotalEmailsNotSpam();
        if (total == 0) return 0.5;
        return (double) estatisticas.getTotalEmailsSpam() / total;
    }

    private double calcularPriorNotSpam() {
        int total = estatisticas.getTotalEmailsSpam() + estatisticas.getTotalEmailsNotSpam();
        if (total == 0) return 0.5;
        return (double) estatisticas.getTotalEmailsNotSpam() / total;
    }
}
