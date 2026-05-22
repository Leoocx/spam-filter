package service;
import repository.PalavraRepository;

import java.util.ArrayList;


public class NaiveBayesService {
    private PalavraRepository palavraRepository = new PalavraRepository(); 

    
    /*
        Método principal, calcula a probabilidade de uma palavra ser spam
    */

    public static boolean isSpam(ArrayList<String> arr){

        /*
            if (probabilidade > *){ 
                
            }
        
        */

        return false;
    }




}


/*
A probabilidade de um e-mail ser spam S, dado que uma determinada palavra W aparece, 
é definida pelo lado esquerdo da equação acima, Pr(S|W) .

O lado direito da equação apresenta a fórmula para calcular essa probabilidade. É ela:

    a probabilidade de a palavra ocorrer no e-mail, dado que se trata de um e-mail de spam, 
    Pr(W|S) multiplicada pela probabilidade de um e-mail ser spam, Pr(S) .
    dividimos a probabilidade de a palavra ocorrer no e-mail, dado que se trata de um e-mail de spam, 
    pelo resultado da multiplicação pela probabilidade de um e-mail ser spam.
    mais a probabilidade de a palavra ocorrer no e-mail, dado que é um e-mail não spam Pr(W|¬S) 
    multiplicada pela probabilidade de um e-mail não ser spam Pr(¬S) .



*/