package com.example.kinhangpoon.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KinhangPoon on 6/2/2018.
 */

public class TimeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Alarm";
    public static final String TABLE_NAME="mytable";
    public static final String KEY_ID ="myid";
    public static final String HOUR = "myhour";
    public static final String MINUTE ="myminute";
    public static final String CHECKBOX ="mycheck";

    public static final int VERSION =1;

    public TimeDbHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + HOUR+ " TEXT,"
                + MINUTE + " TEXT,"  + CHECKBOX + " TEXT" + ")"; // query
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
