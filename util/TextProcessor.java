package util;

import java.util.ArrayList;
import java.util.Arrays;

/*
* 	Responsabilidade:

	Limpar texto
	Tokenizar
	Normalizar (lowercase, remover pontuação)
*
*
*
*/
public class TextProcessor {
    
    public static ArrayList<String> processarTexto(String text){
        if (text==null || text.isEmpty()){
            return new ArrayList<>();
        }

        String processado = text.toLowerCase();
        processado = processado.replaceAll("[^a-zA-Z0-9 ]", "");
        processado=processado.trim().replaceAll("\\d+", "");

        String[] tokens = processado.split("\\s+");


        ArrayList<String> arrText = new ArrayList<>();

        for (String token:  tokens){
            if (token.length()>2 && !desconsiderarPalavra(token)){
                arrText.add(token);
            }
        }
        return arrText;
    }

    private static boolean desconsiderarPalavra(String text){
        ArrayList<String> desconsiderar = new ArrayList<>(Arrays.asList("a", "e","o","que","de","da","do","em","um","para","com","não","uma","os","as","se","se","na","no","por","mais","como","mas","ao","ele","das","dos","aos","foi","era","ser","são"));
        return desconsiderar.contains(text);
    }
}
