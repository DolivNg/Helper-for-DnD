package com.example.halperdnd.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.halperdnd.database.dao.equpment.DataEquipmentImpl;
import com.example.halperdnd.database.dao.simple_table.DataSimpleTableImpl;
import com.example.halperdnd.database.dao.spells.DataSpellsImpl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "";
    private final Context mContext;
    private SQLiteDatabase db;
    private DataSimpleTableImpl dataSimpleTableImpl = null;
    private DataEquipmentImpl dataEquipmentTableImpl = null;
    private DataSpellsImpl dataSpellsImpl = null;


    public DataBaseHelper(Context context, String dbName) throws IOException {
        super(context, dbName, null, 1);
        DB_NAME = dbName;
        this.mContext = context;
        setupDataBase();
    }
    /**getSimpleTAble*/
    public DataSimpleTableImpl getDataResDao() {
        if (dataSimpleTableImpl== null) {
            dataSimpleTableImpl = new DataSimpleTableImpl(db);
        }
        return dataSimpleTableImpl;
    }
    /**getEquipmentTable*/
    public DataEquipmentImpl getDataEquipmentResDao() {
        if (dataEquipmentTableImpl== null) {
            dataEquipmentTableImpl = new DataEquipmentImpl(db);
        }
        return dataEquipmentTableImpl;
    }
    /**getSpellsTable*/
    public DataSpellsImpl getDataSpellsResDao() {
        if (dataSpellsImpl== null) {
            dataSpellsImpl = new DataSpellsImpl(db);
        }
        return dataSpellsImpl;
    }
    private void setupDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){
            //ничего не делать - база уже есть
            //SQLiteDatabase.deleteDatabase(new File(mContext.getDatabasePath(DB_NAME).getPath()));

        }else{
            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();
            try {

                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
        db = openDataBase();
    }
//------------------------------------------
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = mContext.getDatabasePath(DB_NAME).getPath() ;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){


            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    //---------------------------------------
    private void copyDataBase() throws IOException{
        //Открываем локальную БД как входящий поток

        InputStream myInput = mContext.getAssets().open("databases/"+DB_NAME);

       // Log.d("vasa", mContext.getAssets().);
        //Путь ко вновь созданной БД
        String outFileName = mContext.getDatabasePath(DB_NAME).getPath();

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public SQLiteDatabase openDataBase() throws SQLException {

        String myPath = mContext.getDatabasePath(DB_NAME).getPath(); ;
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    @Override
    public synchronized void close() {
        if(db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // onCreate(db);

    }
}
