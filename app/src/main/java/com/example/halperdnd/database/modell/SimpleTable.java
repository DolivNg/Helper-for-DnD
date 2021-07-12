package com.example.halperdnd.database.modell;

import java.util.ArrayList;

public class SimpleTable {

    private ArrayList<ElemetSimpleTable> elemetSimpleTableArrayList;
    private String table_name;
    private int dice;
    int id;
    private String table_name_db;

    public SimpleTable(int id,String table_name, int dice, String table_name_db) {
        this.id = id;
        this.table_name = table_name;
        this.dice = dice;
        this.table_name_db = table_name_db;
    }
    public String getRessult(int ressDice)
    {
        for(ElemetSimpleTable i : elemetSimpleTableArrayList)
        {
            if (i.getMin() >= ressDice &&
                    ressDice <= i.getMax())
                return i.getRessult();
        }
        return "";
    }

    public ArrayList<ElemetSimpleTable> getElemetSimpleTableArrayList() {
        return elemetSimpleTableArrayList;
    }


    public String getTable_name() {
        return table_name;
    }

    public int getDice() {
        return dice;
    }

    public String getTable_name_db() {
        return table_name_db;
    }

    public void setElemetSimpleTableArrayList(ArrayList<ElemetSimpleTable> elemetSimpleTableArrayList) {
        this.elemetSimpleTableArrayList = elemetSimpleTableArrayList;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setTable_name_db(String table_name_db) {
        this.table_name_db = table_name_db;
    }


}
