package com.example.as4;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DataBaseManagement extends SQLiteOpenHelper {

    public static final String DATA_NAME = "spend.db";
    public static final String TABLE_NAME = "Spend";
    public static final String COL_NAME_ITEM = "Item";
    public static final String COL_NAME_DATE = "Date";
    public static final String COL_NAME_PRICE = "Price";

    public DataBaseManagement(Context context){
        super(context, DATA_NAME, null, 4);
    }

    public void onCreate(SQLiteDatabase sqlDB){
        sqlDB.execSQL("CREATE TABLE " + TABLE_NAME + " (Count INTEGER PRIMARY KEY AUTOINCREMENT, Item varchar(100), Date varchar(100), Price DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int older, int newer){
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }


    public boolean add(TransModel mo){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME_ITEM, mo.mItem);
        contentValues.put(COL_NAME_DATE, mo.mDate);
        contentValues.put(COL_NAME_PRICE, mo.mPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor pullData() {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor res = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}