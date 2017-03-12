package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sayar.sila.bebetakvim.activities.Diaper;
import com.sayar.sila.bebetakvim.activities.Sleep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 20.09.2016.
 */
public class DiaperDataSource {
    // Database fields
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] diaperColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_DIAPER, DatabaseHelper.COLUMN_TIME, DatabaseHelper.COLUMN_DATE };

    public DiaperDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Diaper createDiaper(String diaper, String time, String date) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DIAPER, diaper);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        long insertId = database.insert(DatabaseHelper.TABLE_DIAPER, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_DIAPER,
                diaperColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Diaper newDiaper = cursorToDiaper(cursor);
        cursor.close();
        return newDiaper;
    }

    public static void deleteDiaper(Diaper diaper) {
        long id = diaper.getId();
        System.out.println("Diaper deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_DIAPER, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Diaper> getAllDiapers() {
        List<Diaper> diapers = new ArrayList<Diaper>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_DIAPER,
                diaperColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diaper diaper = cursorToDiaper(cursor);
            diapers.add(diaper);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return diapers;
    }

    public List<Diaper> getDayDiapers(String date) {
        List<Diaper> diapers = new ArrayList<Diaper>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_DIAPER,
                diaperColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diaper diaper = cursorToDiaper(cursor);
            if(diaper.getDate().equals(date))
                diapers.add(diaper);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return diapers;
    }

    private Diaper cursorToDiaper(Cursor cursor) {
        Diaper diaper = new Diaper();
        diaper.setId(cursor.getLong(0));
        diaper.setDiaper(cursor.getString(1));
        diaper.setTime(cursor.getString(2));
        diaper.setDate(cursor.getString(3));
        return diaper;
    }
}
