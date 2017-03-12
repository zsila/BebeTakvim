package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sayar.sila.bebetakvim.activities.Sleep;
import com.sayar.sila.bebetakvim.activities.Sleeptime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 20.10.2016.
 */
public class SleeptimeDataSource {
    // Database fields
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] sleeptimeColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_SLEEPTIME };

    public SleeptimeDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Sleeptime createSleeptime(String time) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SLEEPTIME, time);
        long insertId = database.insert(DatabaseHelper.TABLE_SLEEPTIME, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_SLEEPTIME,
                sleeptimeColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Sleeptime newSleeptime = cursorToSleeptime(cursor);
        cursor.close();
        return newSleeptime;
    }

    public static void deleteSleeptime() {
        database.delete(DatabaseHelper.TABLE_SLEEPTIME, null, null);
    }

    public boolean isEmpty(){
        Cursor mCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_SLEEPTIME, null);
        Boolean rowExists;
        if (mCursor.moveToFirst())
        {
            rowExists = false;

        } else
        {
            rowExists = true;
        }
        return rowExists;
    }

    public Sleeptime getSleeptime() {
        Sleeptime time=new Sleeptime();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SLEEPTIME,
                sleeptimeColumns, null, null, null, null, null);

        cursor.moveToFirst();
        time = cursorToSleeptime(cursor);

        // make sure to close the cursor
        cursor.close();
        return time;
    }

    private Sleeptime cursorToSleeptime(Cursor cursor) {
        Sleeptime sleep = new Sleeptime();
        sleep.setId(cursor.getLong(0));
        sleep.setStart(cursor.getString(1));
        return sleep;
    }

}
