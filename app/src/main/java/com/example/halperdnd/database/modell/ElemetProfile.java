package com.example.halperdnd.database.modell;

public class ElemetProfile {
    private int id;
    private String nameProfile;


    public ElemetProfile(int id, String nameProfile) {
        this.id = id;
        this.nameProfile = nameProfile;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setNameProfile(String nameProfile) {
        this.nameProfile = nameProfile;
    }



    public int getId() {
        return id;
    }

    public String getNameProfile() {
        return nameProfile;
    }
}
