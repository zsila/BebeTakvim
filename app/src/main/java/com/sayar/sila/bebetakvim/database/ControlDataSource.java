package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sayar.sila.bebetakvim.activities.Control;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 6.10.2016.
 */
public class ControlDataSource {

    public static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_BOY, DatabaseHelper.COLUMN_KILO, DatabaseHelper.COLUMN_NOT, DatabaseHelper.COLUMN_TIME, DatabaseHelper.COLUMN_DATE };

    private String[] dateColumn = { DatabaseHelper.COLUMN_DATE};
    public ControlDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Control createControl(String boy, String kilo, String not, String time, String date) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOY, boy);
        values.put(DatabaseHelper.COLUMN_KILO, kilo);
        values.put(DatabaseHelper.COLUMN_NOT, not);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        long insertId = database.insert(DatabaseHelper.TABLE_CONTROL, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTROL,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Control newControl = cursorToControl(cursor);
        cursor.close();
        return newControl;
    }

    public static void deleteControl(Control control) {
        long id = control.getId();
        System.out.println("Control deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_CONTROL, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }


    public List<Control> getAllControls() {
        List<Control> controls = new ArrayList<Control>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTROL,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Control control = cursorToControl(cursor);
            controls.add(control);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return controls;
    }

    public List<String> getControlDates() {
        List<String> dates = new ArrayList<String>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTROL,
                dateColumn, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String control = cursor.getString(0);
            dates.add(control);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return dates;
    }


    private Control cursorToControl(Cursor cursor) {
        Control control = new Control();
        control.setId(cursor.getLong(0));
        control.setBoy(cursor.getString(1));
        control.setKilo(cursor.getString(2));
        control.setNot(cursor.getString(3));
        control.setTime(cursor.getString(4));
        control.setDate(cursor.getString(5));
        return control;
    }

}
