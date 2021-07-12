package com.example.halperdnd.database.modell.contacts;

import android.provider.BaseColumns;

public final class EquipmentTableContract {
    public static final class EquipmentEntry implements BaseColumns {
        public final static String TABLE_NAME = "equipment";

        public final static String _ID = "_id";
        public final static String COLUMN_COIN_ID="coin_id";
        public final static String COLUMN_TYPE_ID="type_id";
        public final static String COLUMN_CATEGORY_ID="category_id";
        public final static String COLUMN_TOOLE_PRICE = "toole_price";
        public final static String COLUMN_TOOLE_NANE = "toole_name";
        public final static String COLUMN_TOOLE_DESCRIPTION = "toole_description";
        public final static String COLUMN_TOOLE_SOURCE = "toole_source";
        public final static String COLUMN_NAME_TOOLE_WIGHT = "toole_wight";
    }
    public static final class TypeEntry implements BaseColumns {
        public final static String TABLE_NAME = "subtype";

        public final static String _ID = "_id";
        public final static String COLUMN_CATEGORY_ID="category_id";
        public final static String COLUMN_NAME="sbt_name";
        public final static String COLUMN_CHANCE="chance";
    }

    public static final class CoinEntry implements BaseColumns {
        public final static String TABLE_NAME = "coin";

        public final static String _ID = "_id";
        public final static String COLUMN_TYPE_PRICE="type_coin";
    }

    public static final class CategoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "category";

        public final static String _ID = "_id";
        public final static String COLUMN_NAME="ct_name";
    }
    public static final class SourceEntry implements BaseColumns {
        public final static String TABLE_NAME = "source";

        public final static String _ID = "_id";
        public final static String COLUMN_NAME="sr_name";
    }

    public static final class MarketEntry implements BaseColumns {
        public final static String TABLE_NAME = "market";

        public final static String _ID = "_id";
        public final static String COLUMN_NAME="name";
        public final static String COLUMN_CITY="city";
    }
    public static final class MarketTableEntry implements BaseColumns {
        public final static String TABLE_NAME = "market_table";

        public final static String _ID = "_id";
        public final static String COLUMN_EQUIPMENT_ID="equipmetnt_id";/**TODO*/
        public final static String COLUMN_MARKET_ID="market_id";
    }


}
