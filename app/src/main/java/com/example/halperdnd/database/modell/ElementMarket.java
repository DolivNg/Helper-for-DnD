package com.example.halperdnd.database.modell;

public class ElementMarket {

    private int _id;
    private String nameMarket;
    private String cityMarket;

    public ElementMarket(int _id, String nameMarket, String cityMarket) {
        this._id = _id;
        this.nameMarket = nameMarket;
        this.cityMarket = cityMarket;
    }


    public int get_id() {
        return _id;
    }

    public void setNameMarket(String nameMarket) {
        this.nameMarket = nameMarket;
    }

    public void setCityMarket(String cityMarket) {
        this.cityMarket = cityMarket;
    }

    public String getNameMarket() {
        return nameMarket;
    }

    public String getCityMarket() {
        return cityMarket;
    }





}
