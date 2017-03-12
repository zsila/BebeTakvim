package com.sayar.sila.bebetakvim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sayar.sila.bebetakvim.activities.Food;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 9.08.2016.
 */
public class FoodDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_COMMENT, DatabaseHelper.COLUMN_TIME, DatabaseHelper.COLUMN_DATE };

    public FoodDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Food createComment(String comment, String time, String date) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COMMENT, comment);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        long insertId = database.insert(DatabaseHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Food newFood = cursorToComment(cursor);
        cursor.close();
        return newFood;
    }

    public static void deleteComment(Food food) {
        long id = food.getId();
        System.out.println("Food deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_COMMENTS, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }


    public List<Food> getAllComments() {
        List<Food> foods = new ArrayList<Food>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food food = cursorToComment(cursor);
            foods.add(food);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return foods;
    }

    public List<Food> getDayComments(String date) {
        List<Food> foods = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food food = cursorToComment(cursor);
            if(food.getDate().equals(date)) {
                foods.add(food);
            }
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return foods;
    }

    private Food cursorToComment(Cursor cursor) {
        Food food = new Food();
        food.setId(cursor.getLong(0));
        food.setComment(cursor.getString(1));
        food.setTime(cursor.getString(2));
        food.setDate(cursor.getString(3));
        return food;
    }
}
