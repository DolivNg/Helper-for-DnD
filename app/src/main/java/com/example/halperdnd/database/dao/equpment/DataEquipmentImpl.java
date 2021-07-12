package com.example.halperdnd.database.dao.equpment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.halperdnd.database.modell.ElementEquipment;
import com.example.halperdnd.database.modell.ElementMarket;
import com.example.halperdnd.database.modell.OptionEquipment;
import com.example.halperdnd.database.modell.contacts.EquipmentTableContract;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataEquipmentImpl implements DataEquipmentTableDao {
    private SQLiteDatabase db;

    @Override
    public int insertElementMarket(String name, String city) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EquipmentTableContract.MarketEntry.COLUMN_NAME,name);
        contentValues.put(EquipmentTableContract.MarketEntry.COLUMN_CITY,city);
        return (int) db.insert(EquipmentTableContract.MarketEntry.TABLE_NAME,null,contentValues);

    }

    public DataEquipmentImpl(SQLiteDatabase db){
        this.db=db;
    }
    @Override
    public List<ElementEquipment> loadEquipmentTable() {
        ArrayList<ElementEquipment> elementEquipmentList = new ArrayList<>();

        String query = "SELECT eq."
                + EquipmentTableContract.EquipmentEntry._ID +
                ", cat."
                + EquipmentTableContract.CategoryEntry.COLUMN_NAME +
                ", typ."
                + EquipmentTableContract.TypeEntry.COLUMN_NAME+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_NANE+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_PRICE+
                ", con."
                + EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_NAME_TOOLE_WIGHT+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_DESCRIPTION+
                ", sour."
                + EquipmentTableContract.SourceEntry.COLUMN_NAME+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE+
                " FROM "
                + EquipmentTableContract.EquipmentEntry.TABLE_NAME+
                " eq INNER JOIN "
                + EquipmentTableContract.TypeEntry.TABLE_NAME+
                " typ , "
                + EquipmentTableContract.CoinEntry.TABLE_NAME+
                " con , "
                + EquipmentTableContract.CategoryEntry.TABLE_NAME +
                " cat , "
                + EquipmentTableContract.SourceEntry.TABLE_NAME+
                " sour ON eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_COIN_ID+
                " = con."
                + EquipmentTableContract.CoinEntry._ID+
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID+
                " = cat."
                + EquipmentTableContract.CategoryEntry._ID +
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID+
                " = typ."
                + EquipmentTableContract.TypeEntry._ID+
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE+
                " = sour."
                + EquipmentTableContract.SourceEntry._ID+
                " ;";
        Log.d("vasa", query);
        /**
         SELECT eq._id, cat.name, typ.name, eq.toole_name, eq.toole_price, con.type_price, eq.toole_wight, eq.toole_description,
         sour.name, eq.category_id, eq.type_id, eq.toole_source
         FROM equipment eq INNER JOIN toole_type typ , coin con , category cat , source sour
         ON eq.coin_id = con._id AND eq.category_id = cat._id AND eq.type_id = typ._id AND eq.toole_source = sour._id ;
         * */

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{

                /**Add element to */
                //cursor.getColumnIndex()
                elementEquipmentList.add(
                        new ElementEquipment(
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry._ID)),
                                //cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.TABLE_NAME),
                                cursor.getString(cursor.getColumnIndex( EquipmentTableContract.CategoryEntry.COLUMN_NAME)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.TypeEntry.COLUMN_NAME)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_NANE)),
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_PRICE)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_NAME_TOOLE_WIGHT)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.SourceEntry.COLUMN_NAME)),
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID)),
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID)),
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE))
                        )
                );

            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return elementEquipmentList;
    }

    @Override
    public void insertEquipment( int category_id,String toole_name,int toole_price,int  coin_id, String toole_wight,String toole_description,int toole_source,int type_id ) {

        db.execSQL("INSERT INTO equipment( category_id, toole_name, toole_price, coin_id, toole_wight, toole_description, toole_source, type_id) VALUES ( "+category_id+ ",\""+toole_name+"\","+toole_price+","+
                coin_id+",\""+toole_wight+"\",\""+toole_description+"\",2,"+type_id+");");
        Log.d("vasa", "INSERT INTO equipment( category_id, toole_name, toole_price, coin_id, toole_wight, toole_description, toole_source, type_id) VALUES ( "+category_id+ ",\""+toole_name+"\","+toole_price+","+ coin_id+","+toole_wight+",\""+toole_description+"\",2,"+type_id+");");
    }

    @Override
    public void deleteEquipmentElement(int id) {
        //db.delete(EquipmentTableContract.EquipmentEntry.TABLE_NAME,EquipmentTableContract.EquipmentEntry._ID+ "=" + id,null);
        db.execSQL("DELETE FROM equipment WHERE _id = "+id+";");
        Log.d("vasa", "id delete "+id);
    }

    @Override
    public List<String> loadEquipmentType() {
        ArrayList<String> equipmentType = new ArrayList<>();
        String query = "SELECT "
            +EquipmentTableContract.CategoryEntry.COLUMN_NAME+
            " FROM " +EquipmentTableContract.CategoryEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                equipmentType.add(cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CategoryEntry.COLUMN_NAME)) );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return equipmentType;
    }

    @Override
    public List<String> loadEquipmentSubtype() {
        ArrayList<String> equipmentType = new ArrayList<>();
        String query = "SELECT "
                +EquipmentTableContract.TypeEntry.COLUMN_NAME+
                " FROM " +EquipmentTableContract.TypeEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                equipmentType.add(cursor.getString(cursor.getColumnIndex(EquipmentTableContract.TypeEntry.COLUMN_NAME)) );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return equipmentType;
    }

    @Override
    public List<OptionEquipment> loadOptionEquipment() {
        ArrayList<OptionEquipment> equipmentType = new ArrayList<>();
        String query = "SELECT "
                +EquipmentTableContract.CategoryEntry._ID+
                " , "
                +EquipmentTableContract.CategoryEntry.COLUMN_NAME+
                " FROM " +EquipmentTableContract.CategoryEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {

            cursor.moveToFirst();
            do{
                /**Add element to */
                equipmentType.add(
                        new OptionEquipment(
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.CategoryEntry._ID)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CategoryEntry.COLUMN_NAME))
                        ));
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        for (OptionEquipment i: equipmentType)
        {

             query = "SELECT "
                    +EquipmentTableContract.TypeEntry._ID+
                    " , "
                    +EquipmentTableContract.TypeEntry.COLUMN_NAME+
                    " FROM " +EquipmentTableContract.TypeEntry.TABLE_NAME+
                    " WHERE "
                    +EquipmentTableContract.TypeEntry.COLUMN_CATEGORY_ID+" = "+
                    i.getCategory_id()+" ;";

             cursor = db.rawQuery(query , null);
            try {
                if (cursor.moveToFirst()) {
                    cursor.moveToFirst();
                    do {
                        /**Add element to */

                        i.addSubType(
                                cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.TypeEntry._ID)),
                                cursor.getString(cursor.getColumnIndex(EquipmentTableContract.TypeEntry.COLUMN_NAME))
                        );
                    }
                    while (cursor.moveToNext());
                }
            } finally {
                // Всегда закрываем курсор после чтения
                /**Close cursor*/
                cursor.close();
            }
        }
        return equipmentType;
    }

    @Override
    public int insertMarketElement(int market_id, int equipment_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EquipmentTableContract.MarketTableEntry.COLUMN_MARKET_ID,market_id);
        contentValues.put(EquipmentTableContract.MarketTableEntry.COLUMN_EQUIPMENT_ID,equipment_id);
        return (int) db.insert(EquipmentTableContract.MarketTableEntry.TABLE_NAME,null,contentValues);

    }

    @Override
    public void deleteMarketElements(int market_id) {
        db.execSQL("DELETE FROM "+EquipmentTableContract.MarketTableEntry.TABLE_NAME+" WHERE "+EquipmentTableContract.MarketTableEntry.COLUMN_MARKET_ID+" = "+market_id+";");
    }

    @Override
    public void deleteMarketElements(int market_id, int equipment_id) {
        Log.d("vasa", "id delete "+"DELETE FROM "
                +EquipmentTableContract.MarketTableEntry.TABLE_NAME+
                " WHERE "
                +EquipmentTableContract.MarketTableEntry.COLUMN_MARKET_ID+" = "+market_id+
                " AND "
                + EquipmentTableContract.MarketTableEntry.COLUMN_EQUIPMENT_ID+" = "+equipment_id+";");
        db.execSQL("DELETE FROM "
                +EquipmentTableContract.MarketTableEntry.TABLE_NAME+
                " WHERE "
                +EquipmentTableContract.MarketTableEntry.COLUMN_MARKET_ID+" = "+market_id+
                " AND "
                + EquipmentTableContract.MarketTableEntry.COLUMN_EQUIPMENT_ID+" = "+equipment_id+";");
    }

    @Override
    public List<Integer> chanceSubType() {
        ArrayList<Integer> equipmentChance = new ArrayList<>();
        String query = "SELECT "
                +EquipmentTableContract.TypeEntry.COLUMN_CHANCE+
                " FROM " +EquipmentTableContract.TypeEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{

                equipmentChance.add(cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.TypeEntry.COLUMN_CHANCE)) );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return equipmentChance;
    }

    /**load market*/
    @Override
    public List<ElementMarket> loadElementMarket() {
        List<ElementMarket> elementMarketList = new ArrayList<>();
        String query = "SELECT "
                +EquipmentTableContract.MarketEntry._ID+
                " , "
                +EquipmentTableContract.MarketEntry.COLUMN_NAME+
                " , "
                +EquipmentTableContract.MarketEntry.COLUMN_CITY+
                " FROM "
                +EquipmentTableContract.MarketEntry.TABLE_NAME+
                " ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    /**Add element to */

                    elementMarketList.add( new ElementMarket(
                            cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.MarketEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(EquipmentTableContract.MarketEntry.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(EquipmentTableContract.MarketEntry.COLUMN_CITY))
                    ));
                }
                while (cursor.moveToNext());
            }
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }

        return elementMarketList;
    }

    @Override
    public void deleteMarket(int id) {
        db.execSQL("DELETE FROM "+EquipmentTableContract.MarketEntry.TABLE_NAME+" WHERE _id = "+id+";");
    }

    @Override
    public List<String> loadCoinType() {
        ArrayList<String> equipmentType = new ArrayList<>();
        String query = "SELECT "
                +EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE+
                " FROM " +EquipmentTableContract.CoinEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                equipmentType.add(cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE)) );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return equipmentType;
    }

    @Override
    public List<ElementEquipment> loadEquipmentFromMarketTable(int id) {
        ArrayList<ElementEquipment> elementEquipmentList = new ArrayList<>();

        String query = "SELECT eq."
                + EquipmentTableContract.EquipmentEntry._ID +
                ", cat."
                + EquipmentTableContract.CategoryEntry.COLUMN_NAME +
                ", typ."
                + EquipmentTableContract.TypeEntry.COLUMN_NAME+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_NANE+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_PRICE+
                ", con."
                + EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_NAME_TOOLE_WIGHT+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_DESCRIPTION+
                ", sour."
                + EquipmentTableContract.SourceEntry.COLUMN_NAME+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID+
                ", eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE+
                " FROM "
                + EquipmentTableContract.EquipmentEntry.TABLE_NAME+
                " eq INNER JOIN "
                + EquipmentTableContract.TypeEntry.TABLE_NAME+
                " typ , "
                + EquipmentTableContract.CoinEntry.TABLE_NAME+
                " con , "
                + EquipmentTableContract.CategoryEntry.TABLE_NAME +
                " cat , "
                + EquipmentTableContract.MarketTableEntry.TABLE_NAME+
                " mt ,"
                + EquipmentTableContract.SourceEntry.TABLE_NAME+
                " sour ON eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_COIN_ID+
                " = con."
                + EquipmentTableContract.CoinEntry._ID+
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID+
                " = cat."
                + EquipmentTableContract.CategoryEntry._ID +
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID+
                " = typ."
                + EquipmentTableContract.TypeEntry._ID+
                " AND eq."
                + EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE+
                " = sour."
                + EquipmentTableContract.SourceEntry._ID+
                " AND mt."
                +EquipmentTableContract.MarketTableEntry.COLUMN_MARKET_ID+
                " = "
                +id+
                " AND mt."
                +EquipmentTableContract.MarketTableEntry.COLUMN_EQUIPMENT_ID+
                " = eq."
                +EquipmentTableContract.EquipmentEntry._ID+
                " ;";
        //Log.d("vasa", query);
        /**
         SELECT eq._id, cat.name, typ.name, eq.toole_name, eq.toole_price, con.type_price, eq.toole_wight, eq.toole_description,
         sour.name, eq.category_id, eq.type_id, eq.toole_source
         FROM equipment eq INNER JOIN toole_type typ , coin con , category cat , source sour
         ON eq.coin_id = con._id AND eq.category_id = cat._id AND eq.type_id = typ._id AND eq.toole_source = sour._id ;
         * */

        Cursor cursor = db.rawQuery(query , null);
        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {

                    /**Add element to */
                    //cursor.getColumnIndex()
                    elementEquipmentList.add(
                            new ElementEquipment(
                                    cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry._ID)),
                                    //cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.TABLE_NAME),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CategoryEntry.COLUMN_NAME)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.TypeEntry.COLUMN_NAME)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_NANE)),
                                    cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_PRICE)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.CoinEntry.COLUMN_TYPE_PRICE)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_NAME_TOOLE_WIGHT)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_DESCRIPTION)),
                                    cursor.getString(cursor.getColumnIndex(EquipmentTableContract.SourceEntry.COLUMN_NAME)),
                                    cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_CATEGORY_ID)),
                                    cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TYPE_ID)),
                                    cursor.getInt(cursor.getColumnIndex(EquipmentTableContract.EquipmentEntry.COLUMN_TOOLE_SOURCE))
                            )
                    );

                }
                while (cursor.moveToNext());
            }
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return elementEquipmentList;
    }

}
