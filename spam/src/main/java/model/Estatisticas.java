package model;

public class Estatisticas {
    
    private int id;
    private int totalPalavrasSpam;
    private int totalPalavrasNotSpam;
    private int totalEmailsSpam;
    private int totalEmailsNotSpam;
    
    public Estatisticas(int id, int totalPalavrasSpam, int totalPalavrasNotSpam, int totalEmailsSpam, int totalEmailsNotSpam){
        this.id = id;
        this.totalPalavrasSpam = totalPalavrasSpam;
        this.totalPalavrasNotSpam = totalPalavrasNotSpam;
        this.totalEmailsSpam = totalEmailsSpam;
        this.totalEmailsNotSpam = totalEmailsNotSpam;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getTotalPalavrasSpam() {
        return totalPalavrasSpam;
    }
    public void setTotalPalavrasSpam(int totalPalavrasSpam) {
        this.totalPalavrasSpam = totalPalavrasSpam;
    }
    public int getTotalPalavrasNotSpam() {
        return totalPalavrasNotSpam;
    }
    public void setTotalPalavrasNotSpam(int totalPalavrasNotSpam) {
        this.totalPalavrasNotSpam = totalPalavrasNotSpam;
    }
    public int getTotalEmailsSpam() {
        return totalEmailsSpam;
    }
    public void setTotalEmailsSpam(int totalEmailsSpam) {
        this.totalEmailsSpam = totalEmailsSpam;
    }
    public int getTotalEmailsNotSpam() {
        return totalEmailsNotSpam;
    }
    public void setTotalEmailsNotSpam(int totalEmailsNotSpam) {
        this.totalEmailsNotSpam = totalEmailsNotSpam;
    }
    


}
