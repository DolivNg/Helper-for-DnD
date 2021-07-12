package com.example.halperdnd.database.modell.contacts;

import android.provider.BaseColumns;

public final class SpellsTableContract {
    public static final class SpellEntry implements BaseColumns {
        public final static String TABLE_NAME = "spells";

        public final static String _ID = "_id";
        public final static String COLUMN_COMPONENTS = "components";
        public final static String COLUMN_DISTANCE= "distance";
        public final static String COLUMN_DURATION = "duration";
        public final static String COLUMN_LEVEL = "level_id";
        public final static String COLUMN_MAT_COMPONENTS= "mat_components";
        public final static String COLUMN_SCHOOL = "school_id";
        public final static String COLUMN_SOURCE= "source_id";
        public final static String COLUMN_SPELL_DESCRIPTION= "spell_description";
        public final static String COLUMN_SPELL_NAME= "spell_name";
        public final static String COLUMN_TIME_CAST= "time_cast";
    }
    public static final class ClassEntry implements BaseColumns {
        public final static String TABLE_NAME = "class";

        public final static String _ID = "_id";
        public final static String COLUMN_CLASS_NAME = "class_name";
    }

    public static final class LevelEntry implements BaseColumns {
        public final static String TABLE_NAME = "level";

        public final static String _ID = "_id";
        public final static String COLUMN_LEVEL_NAME = "level_name";
    }

    public static final class SchoolEntry implements BaseColumns {
        public final static String TABLE_NAME = "school";

        public final static String _ID = "_id";
        public final static String COLUMN_SCHOOL_NAME = "school_name";
    }

    public static final class SourceEntry implements BaseColumns {
        public final static String TABLE_NAME = "source";

        public final static String _ID = "_id";
        public final static String COLUMN_BOOK = "book";
    }
    public static final class SpellAndClassEntry implements BaseColumns {
        public final static String TABLE_NAME = "spell_mm_class";

        public final static String _ID = "_id";
        public final static String COLUMN_CLASS_ID = "class_id";
        public final static String COLUMN_SPELL_ID = "spell_id";
    }
    public static final class ProfilesEntry implements BaseColumns {
        public final static String TABLE_NAME = "profiles";

        public final static String _ID = "_id";
        public final static String COLUMN_NAME="name";
        public final static String COLUMN_BASE="base";
    }

    public static final class SpellListEntry implements BaseColumns {
        public final static String TABLE_NAME = "spellList";

        public final static String _ID = "_id";
        public final static String COLUMN_PROFILL_ID="profill_id";
        public final static String COLUMN_SPELL_ID="spell_id";
    }


}
