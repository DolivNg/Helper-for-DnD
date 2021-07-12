package com.example.halperdnd.database.dao.equpment;

import android.app.ListActivity;

import com.example.halperdnd.database.modell.ElementEquipment;
import com.example.halperdnd.database.modell.ElementMarket;
import com.example.halperdnd.database.modell.OptionEquipment;

import java.util.List;

public interface DataEquipmentTableDao {
     List<ElementEquipment> loadEquipmentTable();
     void insertEquipment(int category_id,String toole_name,int toole_price,int  coin_id, String toole_wight,String toole_description,int toole_source,int type_id);
     void deleteEquipmentElement(int _id);
     List<String> loadEquipmentType();
     List<String> loadCoinType();
     List<String> loadEquipmentSubtype();
     List<OptionEquipment> loadOptionEquipment();
     List<ElementMarket> loadElementMarket();
     void deleteMarket(int id);
     int insertElementMarket(String name, String city);
     int insertMarketElement(int market_id, int equipment_id);
     void deleteMarketElements(int market_id);
     void deleteMarketElements(int market_id,int equipment_id);
     List<Integer> chanceSubType();
     List<ElementEquipment> loadEquipmentFromMarketTable(int id);
}
