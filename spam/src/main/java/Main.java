import repository.*;
import service.*;
import util.TextProcessor;
import model.Estatisticas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            // padrão SQLite
            DatabaseInitializer.init();
            conn = ConnectionManager.getConnection();

            // Carrega estatísticas
            EstatisticasRepository estRepo = new EstatisticasRepository();
            Estatisticas estatisticas;
            if (!estRepo.existeEstatisticas()) {
                estatisticas = new Estatisticas(1, 0, 0, 0, 0);
                estRepo.inserirEstatisticas(estatisticas);
            } else {
                estatisticas = estRepo.buscarEstatisticas();
            }

            //
            NaiveBayesService naiveBayes = new NaiveBayesService(estatisticas);
            TreinamentoService treinamento = new TreinamentoService(estatisticas);
            TextProcessor processor = new TextProcessor();
            ClassificacaoService classificacao = new ClassificacaoService(naiveBayes, treinamento, processor);

            System.out.println("=== Filtro de Spam Naive Bayes (SQLite) ===\n");

            while (true) {
                System.out.println("\n[1]. CLASSIFICAR EMAIL");
                System.out.println("[2]. TREINAR MANUALMENTE");
                System.out.println("[3]. OBTER ESTATISTÍCAS");
                System.out.println("[4]. TREINAR DATASET");
                System.out.println("[5]. Sair");
                System.out.print("OPÇÃO: ");

                String linha = reader.readLine();
                if (linha == null) break;
                int op = Integer.parseInt(linha.trim());

                if (op == 1) {
                    System.out.print("EMAIL: ");
                    String email = reader.readLine();
                    boolean isSpam = classificacao.processarEmail(email, true);
                    System.out.println("RESULTADO: " + (isSpam ? "SPAM" : "NÃO SPAM"));
                } else if (op == 2) {
                    System.out.print("EMAIL: ");
                    String email = reader.readLine();
                    System.out.print("É SPAM? (S/N): ");
                    String resp = reader.readLine();
                    boolean isSpam = resp != null && resp.equalsIgnoreCase("S");
                    treinamento.treinar(processor.processarTexto(email), isSpam);
                    System.out.println("TREINADO");
                } else if (op == 3) {
                    System.out.println("EMAILS SPAM: " + estatisticas.getTotalEmailsSpam());
                    System.out.println("EMAILS NÃO SPAM: " + estatisticas.getTotalEmailsNotSpam());
                    System.out.println("PALAVRAS ÚNICAS: " + new PalavraRepository().getVocabSize());
                }
                else if (op == 4) {

                    BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));

                    String linhaCsv;

                    br.readLine();

                    int total = 0;

                    while ((linhaCsv = br.readLine()) != null) {

                        String[] partes = linhaCsv.split(",", 2);

                        if (partes.length < 2) continue;

                        boolean isSpam = partes[0].equalsIgnoreCase("spam");

                        List<String> palavras = processor.processarTexto(partes[1]);

                        treinamento.treinar(palavras, isSpam);

                        total++;

                        if (total % 100 == 0) {
                            System.out.println(total + " EMAILS TREINADOS...");
                        }
                    }

                    br.close();

                    System.out.println("TREINAMENTO CONCLUIDO!");
                    System.out.println("TOTAL TREINADO: " + total);
                }
                else if (op == 5) {
                    break;
                } else {
                    System.out.println("OPÇÃO ÍNVALIDA!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}