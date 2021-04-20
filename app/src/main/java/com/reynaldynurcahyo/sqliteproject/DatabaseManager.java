package com.reynaldynurcahyo.sqliteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {

    private static final String ROW_ID = "_id";
    private static final String ROW_NAME = "name";
    private static final String ROW_PRICE = "price";

    private static final String DB_NAME = "MainActivity";
    private static final String TABLE_NAME = "games";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + ROW_ID + " integer PRIMARY KEY autoincrement,"
            + ROW_NAME + " text,"
            + ROW_PRICE + " text)";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void addRow(String name, String price) {
        ContentValues values = new ContentValues();
        values.put(ROW_NAME, name);
        values.put(ROW_PRICE, price);

        try {
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> getAllRow() {
        ArrayList<ArrayList<Object>> arrayData = new ArrayList<ArrayList<Object>>();
        Cursor cs;

        try {
            cs = db.query(TABLE_NAME,
                    new String[] {
                            ROW_ID,
                            ROW_NAME,
                            ROW_PRICE
                    }, null, null, null, null, null);
            cs.moveToFirst();

            if (!cs.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cs.getLong(0));
                    dataList.add(cs.getString(1));
                    dataList.add(cs.getString(2));
                    arrayData.add(dataList);
                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return arrayData;
    }
}
