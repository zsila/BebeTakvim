package com.sayar.sila.bebetakvim.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zisan on 25.09.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "bebetkvm.db";
    private static final int DATABASE_VERSION = 4;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";

    public static final String TABLE_DIAPER = "diaper";
    public static final String COLUMN_DIAPER = "condition";
    public static final String TABLE_COMMENTS = "yemek";
    public static final String TABLE_CONTROL = "kontrol";
    public static final String TABLE_LANGUAGE = "language";
    public static final String COLUMN_COMMENT = "comment";
    public static final String TABLE_SLEEP = "sleep";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_BOY = "boy";
    public static final String COLUMN_KILO = "kilo";
    public static final String COLUMN_NOT = "note";
    public static final String COLUMN_LANGUAGE = "lang";

    public static final String TABLE_SLEEPTIME = "sleeptime";
    public  static final String COLUMN_SLEEPTIME = "start";


    private static final String CREATE_TABLE_DIAPER = "create table "
            + TABLE_DIAPER + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DIAPER
            + " text not null,"+ COLUMN_DATE + " text not null,"+COLUMN_TIME + " text not null);";

    private static final String CREATE_TABLE_SLEEP = "create table "
            + TABLE_SLEEP + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DURATION
            + " text not null,"+ COLUMN_DATE + " text not null,"+ COLUMN_TIME + " text not null);";

    private static final String CREATE_TABLE_FOOD = "create table "
            + TABLE_COMMENTS  + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null,"+ COLUMN_DATE + " text not null,"+COLUMN_TIME + " text not null);";

    private static final String CREATE_TABLE_CONTROL = "create table "
            + TABLE_CONTROL  + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_BOY
            + " text not null,"+ COLUMN_KILO + " text not null,"+COLUMN_NOT
            + " text not null,"+COLUMN_DATE + " text not null,"+COLUMN_TIME + " text not null);";

    private static final String CREATE_TABLE_SLEEPTIME = "create table "
            + TABLE_SLEEPTIME+ "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SLEEPTIME
            + " text not null);";

    private static final String CREATE_TABLE_LANGUAGE = "create table "
            + TABLE_LANGUAGE+ "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LANGUAGE
            + " text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_SLEEP);
        db.execSQL(CREATE_TABLE_DIAPER);
        db.execSQL(CREATE_TABLE_CONTROL);
        db.execSQL(CREATE_TABLE_SLEEPTIME);
        db.execSQL(CREATE_TABLE_LANGUAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIAPER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTROL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEPTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);

        // create new tables
        onCreate(db);
    }
}
