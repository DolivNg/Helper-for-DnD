package com.example.halperdnd.database.modell;

import android.util.Log;

public class ElementEquipment {
    private int id ;
    private int categoryId;
    private int type_id;
    private String wight;
    private int price;
    private int source;

    private String sourceName;
    private String categoryName= "";
    private String typeName;
    private String typeCoin;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getType_id() {
        return type_id;
    }

    public String getWight() {
        return wight;
    }

    public int getPrice() {
        return price;
    }

    public int getSource() {
        return source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeCoin() {
        return typeCoin;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ElementEquipment(int id, String categoryName, String typeName, String name, int price, String typeCoin, String wight, String description, String sourceName,
                            int categoryId, int type_id, int source) {
        this.id = id;
        this.categoryId = categoryId;
        this.type_id = type_id;
        this.wight = wight;
        this.price = price;
        this.source = source;
        this.sourceName = sourceName;
        this.categoryName = categoryName;
        this.typeName = typeName;
        this.typeCoin = typeCoin;
        this.name = name;
        this.description = description;
    }
}
