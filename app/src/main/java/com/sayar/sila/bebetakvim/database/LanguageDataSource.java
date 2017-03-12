package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.animation.LayoutAnimationController;

import com.sayar.sila.bebetakvim.units.Language;


/**
 * Created by zisan on 17.11.2016.
 */
public class LanguageDataSource {
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] langColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_LANGUAGE };

    public LanguageDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Language createLanguage(String lang) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LANGUAGE, lang);
        long insertId = database.insert(DatabaseHelper.TABLE_LANGUAGE, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_LANGUAGE,
                langColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Language newLang = cursorToLanguage(cursor);
        cursor.close();
        return newLang;
    }

    public static void deleteLanguage() {
        database.delete(DatabaseHelper.TABLE_LANGUAGE, null, null);
    }

    public boolean isEmpty(){
        Cursor mCursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_LANGUAGE, null);
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

    public Language getLanguage() {
        Language time=new Language();
        Cursor cursor = database.query(DatabaseHelper.TABLE_LANGUAGE,
                langColumns, null, null, null, null, null);

        cursor.moveToFirst();
        time = cursorToLanguage(cursor);

        // make sure to close the cursor
        cursor.close();
        return time;
    }

    private Language cursorToLanguage(Cursor cursor) {
        Language sleep = new Language();
        sleep.setId(cursor.getLong(0));
        sleep.setLang(cursor.getString(1));
        return sleep;
    }
}
