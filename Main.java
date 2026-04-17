import java.util.ArrayList;
import java.util.List;

import util.TextProcessor;

public class Main {
    public static void main(String[] args){
        String text = "Frase é qualquer enunciado linguístico com sentido completo que comunica uma ideia, podendo ou não conter verbos, e termina com uma pausa pontuada. Ela transmite uma mensagem de forma independente. Pode ser formada por uma ou mais palavras e é caracterizada pela sua entonação na fala ou pontuação na escrita.";
        ArrayList<String> list = TextProcessor.processarTexto(text);

        for(String i: list){
            System.out.println(i);
        }

        

    }
}


/*
        try(Connection connection = ConnectionDB.conectar()) {
                Statement stmt = connection.createStatement();

                String comandoSQL = "CREATE TABLE IF NOT EXISTS palavras (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "text TEXT NOT NULL," +
                        "freqSpam INTEGER," +
                        "freqNotSpam INTEGER," +
                        ");";
                System.out.println(comandoSQL);

                stmt.execute(comandoSQL);

                System.out.println("Tabela: 'palavras' criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("ERRO AO CRIAR TABELA: " + e.getMessage());
            e.printStackTrace();   
    } */