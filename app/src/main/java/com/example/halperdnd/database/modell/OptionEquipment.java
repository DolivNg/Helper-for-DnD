package com.example.halperdnd.database.modell;

import java.util.ArrayList;
import java.util.List;

public class OptionEquipment {
    int category_id;
    String categoryName;
    List<SubType> subTypeList = new ArrayList<>();

    public List<SubType> getSubTypeList() {
        return subTypeList;
    }

    public void setSubTypeList(List<SubType> subTypeList) {
        this.subTypeList = subTypeList;
    }

    public OptionEquipment(int category_id, String categoryNmae) {
        this.category_id = category_id;
        this.categoryName = categoryNmae;
    }

    public void addSubType(int id,String name) {
        subTypeList.add(new SubType(id,name)) ;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public class SubType{
        private  int _id;

         public int get_id() {
             return _id;
         }

         public String getSubTypeName() {
             return subTypeName;
         }

         private String subTypeName;

         public SubType(int _id, String subTypeName) {
             this._id = _id;
             this.subTypeName = subTypeName;
         }
     }
}
