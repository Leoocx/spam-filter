package service;

import java.util.ArrayList;

import util.TextProcessor;

/*
	Responsabilidade:

	Receber o email
	Chamar o processamento
	Chamar o NaiveBayesService
	Retornar resultado final
    
    */
public class ClassificacaoService {
    

    public static boolean classificar(String email){
        ArrayList<String> arr = TextProcessor.processarTexto(email);
        return NaiveBayesService.isSpam(arr);
    }

}
