package com.example.halperdnd.database.modell.contacts;

import android.provider.BaseColumns;


/**Singleton for SimpleTable.bd*/
public final class SimpleTableContract {
    private SimpleTableContract(){}
    /**CreteHit table*/
    public static final class CreteHitEntry implements BaseColumns {

        public final static String _ID = "_id";
        public final static String COLUMN_MIN= "min";
        public final static String COLUMN_MAX= "max";
        public final static String COLUMN_RESSULT= "ressult";
    }
    /**GlobalSimpleTable table*/
    public static final class GlobalSimpleTableEntry implements BaseColumns {
        public final static String TABLE_NAME = "global_simple_table";

        public final static String _ID = "_id";
        public final static String COLUMN_TABLE_NAME= "table_name";
        public final static String COLUMN_DICE= "dice";
        public final static String COLUMN_TABLE_NAME_DB= "table_name_db";
    }
}
