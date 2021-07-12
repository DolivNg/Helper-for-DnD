package com.example.halperdnd.database.dao.simple_table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.halperdnd.database.dao.simple_table.DataSimpleTableDao;
import com.example.halperdnd.database.modell.ElemetSimpleTable;
import com.example.halperdnd.database.modell.SimpleTable;
import com.example.halperdnd.database.modell.contacts.SimpleTableContract;

import java.util.ArrayList;
import java.util.List;

public class DataSimpleTableImpl implements DataSimpleTableDao {
    private SQLiteDatabase db;

    public DataSimpleTableImpl(SQLiteDatabase db){
        this.db=db;
    }

    @Override
    public ArrayList<ElemetSimpleTable> loadSimpleTableElement(String table) {
        ArrayList<ElemetSimpleTable> tableElement = new ArrayList<>();

        String query = "SELECT "
                + SimpleTableContract.CreteHitEntry._ID +
                ","
                + SimpleTableContract.CreteHitEntry.COLUMN_MIN +
                ","
                + SimpleTableContract.CreteHitEntry.COLUMN_MAX +
                ","
                + SimpleTableContract.CreteHitEntry.COLUMN_RESSULT+
                " FROM "
                + table +
                " ;";
        Cursor cursor = db.rawQuery(query , null);
        try {
        cursor.moveToFirst();
        do{

            /**Add element to */

            tableElement.add(
                    new ElemetSimpleTable(
                            cursor.getInt(cursor.getColumnIndex(SimpleTableContract.CreteHitEntry._ID)),
                            cursor.getInt(cursor.getColumnIndex(SimpleTableContract.CreteHitEntry.COLUMN_MIN)),
                            cursor.getInt(cursor.getColumnIndex(SimpleTableContract.CreteHitEntry.COLUMN_MAX)),
                            cursor.getString(cursor.getColumnIndex(SimpleTableContract.CreteHitEntry.COLUMN_RESSULT))
                    )
                    );
        }
        while (cursor.moveToNext());
    } finally {
        // Всегда закрываем курсор после чтения
        /**Close cursor*/
        cursor.close();
    }
        return tableElement;
    }


    @Override
    public List<SimpleTable> loadSimpleTable() {

        ArrayList<SimpleTable> simpleTableElement = new ArrayList<>();
        Log.d("vasa", "Simple");
        String query = "SELECT "
                + SimpleTableContract.GlobalSimpleTableEntry._ID +
                ","
                + SimpleTableContract.GlobalSimpleTableEntry.COLUMN_TABLE_NAME +
                ","
                + SimpleTableContract.GlobalSimpleTableEntry.COLUMN_DICE +
                ","
                + SimpleTableContract.GlobalSimpleTableEntry.COLUMN_TABLE_NAME_DB+
                " FROM "
                + SimpleTableContract.GlobalSimpleTableEntry.TABLE_NAME +
                " ;";
        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{

                /**Add element to */

                simpleTableElement.add(
                        new SimpleTable(
                                cursor.getInt(cursor.getColumnIndex(SimpleTableContract.GlobalSimpleTableEntry._ID)),
                                cursor.getString(cursor.getColumnIndex(SimpleTableContract.GlobalSimpleTableEntry.COLUMN_TABLE_NAME)),
                                cursor.getInt(cursor.getColumnIndex(SimpleTableContract.GlobalSimpleTableEntry.COLUMN_DICE)),
                                cursor.getString(cursor.getColumnIndex(SimpleTableContract.GlobalSimpleTableEntry.COLUMN_TABLE_NAME_DB))

                        )
                );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }

        return simpleTableElement;
    }
}
