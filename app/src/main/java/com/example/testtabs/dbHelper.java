package com.example.testtabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "IA_projectDB";
    private static final String TABLE_NAME ="IA_dailyLog";
    private static final String TABLE_NAME2 ="IA_settings";

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + TABLE_NAME2 +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, value TEXT)";
        db.execSQL(createTable);

        createTable = "create table " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, date_time TEXT, food_name TEXT, calories INTEGER, notes TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertRowToLog(String date_time, String food_name, int calories, String notes){
        SQLiteDatabase sqlLiteDB = this.getWritableDatabase();
        ContentValues logEntry = new ContentValues();
        logEntry.put("date_time", date_time);
        logEntry.put("food_name", food_name);
        logEntry.put("calories", calories);
        logEntry.put("notes", notes);
        long results = sqlLiteDB.insert(TABLE_NAME, null, logEntry);
        if(results == -1){
            return false;
        }else {
            return  true;
        }
    }
    public boolean insertRowToSettings(String name, String value){
        SQLiteDatabase sqlLiteDB = this.getWritableDatabase();
        ContentValues logEntry = new ContentValues();
        logEntry.put("name", name);
        logEntry.put("value", value);
        long results = sqlLiteDB.insert(TABLE_NAME2, null, logEntry);
        if(results == -1){
            return false;
        }else {
            return  true;
        }
    }
    public boolean updateIfExistsElseInsert(String name, String value){
        SQLiteDatabase sqlLiteDB = this.getWritableDatabase();
        ContentValues logEntry = new ContentValues();
        logEntry.put("name", name);
        logEntry.put("value", value);

        //first, try to update. if fails, then insert
        int rows = sqlLiteDB.update(TABLE_NAME2, logEntry, " name=?", new String[]{name});
        if (rows == 0) { //update return 0, failed to update due to name does not exist
            long results = sqlLiteDB.insert(TABLE_NAME2, null, logEntry);
            if(results == -1){
                return false;
            }else {
                return  true;
            }
        } else{ //update return -1, name exist, but update operation failed
            if(rows == -1){
                return false;
            }else {
                return  true;
            }
        }
    }

    public Cursor getAllRows(){
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor allRows = sqLiteDB.rawQuery("select * from " + TABLE_NAME, null);
        return allRows;
    }
    public Cursor getAllRows2(){
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor allRows = sqLiteDB.rawQuery("select name, value from " + TABLE_NAME2, null);
        return allRows;
    }

    public Cursor getSumByDate(){ //only return last 7 days
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor sumRows = sqLiteDB.rawQuery("select date(date_time), sum(calories) from " + TABLE_NAME
                + " where date(date_time) > (SELECT date('now','-7 day')) group by date(date_time)", null);
        return sumRows;
    }

    public boolean deleteAllRows(){
        SQLiteDatabase sqlLiteDB = this.getWritableDatabase();
        long results = sqlLiteDB.delete(TABLE_NAME,"",null);
        if(results == -1){
            return false;
        }else {
            return  true;
        }
    }
    public boolean deleteAllRows2(){
        SQLiteDatabase sqlLiteDB = this.getWritableDatabase();
        long results = sqlLiteDB.delete(TABLE_NAME2,"",null);
        if(results == -1){
            return false;
        }else {
            return  true;
        }
    }

    public Cursor getTodayCalorie(){
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor todayCalorie = sqLiteDB.rawQuery("select date(date_time), sum(calories) from " + TABLE_NAME
                + " where date(date_time) = (SELECT date('now')) group by date(date_time)", null);
        return todayCalorie;
    }
    public Cursor getCalorieGoal(){
        SQLiteDatabase sqLiteDB = this.getReadableDatabase();
        Cursor calorieGoal = sqLiteDB.rawQuery("select value from " + TABLE_NAME2
                + " where name = 'CalorieGoal'", null);
        return calorieGoal;
    }
}
