package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.sayar.sila.bebetakvim.activities.Sleep;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 31.08.2016.
 */
public class SleepDataSource {
        // Database fields
        private static SQLiteDatabase database;
        private DatabaseHelper dbHelper;
        private String[] sleepColumns = { DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_DURATION, DatabaseHelper.COLUMN_TIME, DatabaseHelper.COLUMN_DATE };

        public SleepDataSource(Context context) {
            dbHelper = new DatabaseHelper(context);

        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public Sleep createSleep(String duration, String time, String date) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_DURATION, duration);
            values.put(DatabaseHelper.COLUMN_TIME, time);
            values.put(DatabaseHelper.COLUMN_DATE, date);
            long insertId = database.insert(DatabaseHelper.TABLE_SLEEP, null,
                    values);
            Cursor cursor = database.query(DatabaseHelper.TABLE_SLEEP,
                    sleepColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Sleep newSleep = cursorToSleep(cursor);
            cursor.close();
            return newSleep;
        }

        public static void deleteSleep(Sleep sleep) {
            long id = sleep.getId();
            System.out.println("Sleep deleted with id: " + id);
            database.delete(DatabaseHelper.TABLE_SLEEP, DatabaseHelper.COLUMN_ID
                    + " = " + id, null);
        }

        public List<Sleep> getAllSleeps() {
            List<Sleep> sleeps = new ArrayList<Sleep>();

            Cursor cursor = database.query(DatabaseHelper.TABLE_SLEEP,
                    sleepColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Sleep sleep = cursorToSleep(cursor);
                sleeps.add(sleep);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return sleeps;
        }

    public List<Sleep> getDaySleeps(String date) {
        List<Sleep> sleeps = new ArrayList<Sleep>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SLEEP,
                sleepColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sleep sleep = cursorToSleep(cursor);
            if(sleep.getDate().equals(date)) {
                sleeps.add(sleep);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sleeps;
    }



        private Sleep cursorToSleep(Cursor cursor) {
            Sleep sleep = new Sleep();
            sleep.setId(cursor.getLong(0));
            sleep.setSleep(cursor.getString(1));
            sleep.setTime(cursor.getString(2));
            sleep.setDate(cursor.getString(3));
            return sleep;
        }
    }

