package com.example.halperdnd.database.dao.spells;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.halperdnd.database.modell.ElementSpellsTable;
import com.example.halperdnd.database.modell.ElemetProfile;
import com.example.halperdnd.database.modell.ElemetSimpleTable;
import com.example.halperdnd.database.modell.SpellAndClass;
import com.example.halperdnd.database.modell.contacts.SimpleTableContract;
import com.example.halperdnd.database.modell.contacts.SpellsTableContract;

import java.util.ArrayList;
import java.util.List;

public class DataSpellsImpl implements DataSpellsTableDao {
    private SQLiteDatabase db;

    @Override
    public SpellAndClass getSpellAndClass(int id) {
        //SpellAndClass sp;
        List<Integer> listInt = new ArrayList<>();
        String query = "SELECT "
                +SpellsTableContract.SpellAndClassEntry.COLUMN_SPELL_ID+
                " FROM "
                +SpellsTableContract.SpellAndClassEntry.TABLE_NAME+
                " WHERE "
                +SpellsTableContract.SpellAndClassEntry.COLUMN_CLASS_ID+
                " = "
                +id+
                ";";

        Cursor cursor = db.rawQuery(query , null);
        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    /**Add element to */
                    listInt.add(
                            cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellAndClassEntry.COLUMN_SPELL_ID))
                    );

                }
                while (cursor.moveToNext());
            }
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return new SpellAndClass(id,listInt);
    }

    public DataSpellsImpl(SQLiteDatabase db){
        this.db=db;
    }

    @Override
    public List<ElementSpellsTable> loadSpellsTable() {
        ArrayList<ElementSpellsTable> spellsTable = new ArrayList<>();

        String query ="SELECT sp._id,sp.source_id, sp.school_id, sp.level_id,sp.time_cast, sp.components, sp.distance, sp.duration, sp.mat_components, sp.spell_name, sp.spell_description , lv.level_name, sour.book, sch.school_name FROM spells sp INNER JOIN  level lv , school sch , source sour ON sp.level_id = lv._id AND sp.school_id = sch._id AND sp.source_id = sour._id ;";
        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{

                /**Add element to */

                spellsTable.add(
                        new ElementSpellsTable(
                                cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellEntry._ID)),
                                cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_SOURCE)),
                                cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_SCHOOL)),
                                cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_LEVEL)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_COMPONENTS)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_DISTANCE)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_DURATION)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_MAT_COMPONENTS)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_SPELL_DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_SPELL_NAME)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SpellEntry.COLUMN_TIME_CAST)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.LevelEntry.COLUMN_LEVEL_NAME)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SourceEntry.COLUMN_BOOK)),
                                cursor.getString(cursor.getColumnIndex(SpellsTableContract.SchoolEntry.COLUMN_SCHOOL_NAME))
                        )
                );
            }
            while (cursor.moveToNext());

        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return spellsTable;
    }

    @Override
    public List<ElemetProfile> loadProfill() {
        ArrayList<ElemetProfile> profiles = new ArrayList<>();
        String query = "SELECT "
                +SpellsTableContract.ProfilesEntry._ID+
                " , "
                + SpellsTableContract.ProfilesEntry.COLUMN_NAME+
                " FROM " +SpellsTableContract.ProfilesEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                profiles.add( new ElemetProfile(
                        cursor.getInt(cursor.getColumnIndex(SpellsTableContract.ProfilesEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(SpellsTableContract.ProfilesEntry.COLUMN_NAME)))
                );
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return profiles;
    }

    @Override
    public void insertProfil(String name) {
        db.execSQL("INSERT INTO "
                +SpellsTableContract.ProfilesEntry.TABLE_NAME+
                "( "+SpellsTableContract.ProfilesEntry.COLUMN_NAME+ ","
                +SpellsTableContract.ProfilesEntry.COLUMN_BASE+
                ") VALUES ( \""+name+ "\","+0+");");
    }

    @Override
    public void addElementInProfile(int profileId,int spell_id) {

        db.execSQL("INSERT INTO "
                +SpellsTableContract.SpellListEntry.TABLE_NAME+
                "( "+SpellsTableContract.SpellListEntry.COLUMN_SPELL_ID+ ","
                +SpellsTableContract.SpellListEntry.COLUMN_PROFILL_ID+
                ") VALUES ( "+spell_id+ ","+ profileId+");");
    }

    @Override
    public void deleteElementFromProfile(int profileId,int spellId) {
        db.execSQL("DELETE FROM "
                +SpellsTableContract.SpellListEntry.TABLE_NAME+
                " WHERE  "
                +SpellsTableContract.SpellListEntry.COLUMN_PROFILL_ID+
                " = "+profileId+" AND "
                +SpellsTableContract.SpellListEntry.COLUMN_SPELL_ID +
                " = " + spellId + ";");
    }

    @Override
    public void deleteProfile(int profileId) {
        db.execSQL("DELETE FROM "
                +SpellsTableContract.ProfilesEntry.TABLE_NAME+
                " WHERE  "
                +SpellsTableContract.ProfilesEntry._ID+
                " = "+profileId+ ";");
    }

    @Override
    public List<String> getClassNames() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT "
                + SpellsTableContract.ClassEntry.COLUMN_CLASS_NAME+
                " FROM " +SpellsTableContract.ClassEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                list.add(cursor.getString(cursor.getColumnIndex(SpellsTableContract.ClassEntry.COLUMN_CLASS_NAME)));
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return list;
    }

    @Override
    public List<String> getSchoolNames() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT "
                + SpellsTableContract.SchoolEntry.COLUMN_SCHOOL_NAME+
                " FROM " +SpellsTableContract.SchoolEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                list.add(cursor.getString(cursor.getColumnIndex(SpellsTableContract.SchoolEntry.COLUMN_SCHOOL_NAME)));
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return list;
    }

    @Override
    public List<String> getSourceNames() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT "
                + SpellsTableContract.SourceEntry.COLUMN_BOOK+
                " FROM " +SpellsTableContract.SourceEntry.TABLE_NAME+" ;";

        Cursor cursor = db.rawQuery(query , null);
        try {
            cursor.moveToFirst();
            do{
                /**Add element to */
                list.add(cursor.getString(cursor.getColumnIndex(SpellsTableContract.SourceEntry.COLUMN_BOOK)));
            }
            while (cursor.moveToNext());
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }
        return list;
    }

    @Override
    public void deleteAllElementFromProfile(int profileId) {
        db.execSQL("DELETE FROM "
                +SpellsTableContract.SpellListEntry.TABLE_NAME+
                " WHERE  "
                +SpellsTableContract.SpellListEntry.COLUMN_PROFILL_ID+
                " = "+profileId+ ";");
    }


    @Override
    public List<Integer> selectElementFromProfile(int profileId) {
        ArrayList<Integer> spellsChoose = new ArrayList<>();

        String query = "SELECT "
                + SpellsTableContract.SpellListEntry.COLUMN_SPELL_ID+
                " FROM  "
                        +SpellsTableContract.SpellListEntry.TABLE_NAME+
                " WHERE "
                +SpellsTableContract.SpellListEntry.COLUMN_PROFILL_ID +
                " = " + profileId + ";";
        Log.d("vasa", "size "+query);
        Cursor cursor = db.rawQuery(query , null);

        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    /**Add element to */

                    spellsChoose.add(
                            cursor.getInt(cursor.getColumnIndex(SpellsTableContract.SpellListEntry.COLUMN_SPELL_ID))
                    );
                }
                while (cursor.moveToNext());
            }
        } finally {
            // Всегда закрываем курсор после чтения
            /**Close cursor*/
            cursor.close();
        }

        Log.d("vasa", "size "+spellsChoose.size());
        return spellsChoose;
    }
}
