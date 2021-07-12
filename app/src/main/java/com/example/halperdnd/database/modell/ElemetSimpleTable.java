package com.example.halperdnd.database.modell;

public class ElemetSimpleTable {

    private int id;
    private int min;
    private int max;
    private String ressult;


   public ElemetSimpleTable(int id, int min, int max, String ressult){
       this.id=id;
       this.min=min;
       this.max=max;
       this.ressult=ressult;

    }
    public void setId(int id) {
        this.id = id;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setRessult(String ressult) {
        this.ressult = ressult;
    }

    public int getId() {
        return id;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getRessult() {
        return ressult;
    }
}
