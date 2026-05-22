package model;

public class Palavra {
    private int id;
    private String text;
    private int freqSpam;
    private int freqNotSpam;


    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getFreqSpam() {
        return freqSpam;
    }
    public void setFreqSpam(int freqSpam) {
        this.freqSpam = freqSpam;
    }
    public int getFreqNotSpam() {
        return freqNotSpam;
    }
    public void setFreqNotSpam(int freqNotSpam) {
        this.freqNotSpam = freqNotSpam;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
