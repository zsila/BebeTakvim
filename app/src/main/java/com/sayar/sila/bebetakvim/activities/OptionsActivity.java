package com.sayar.sila.bebetakvim.activities;

/**
 * Created by zisan on 9.08.2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.database.ControlDataSource;
import com.sayar.sila.bebetakvim.database.DiaperDataSource;
import com.sayar.sila.bebetakvim.database.FoodDataSource;
import com.sayar.sila.bebetakvim.database.SleepDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends Activity {
    ImageView imgLunch = null;
    ImageView imgSleep = null;
    ImageView imgDiaper = null;
    ImageView imgControl = null;
    CalendarView cal = null;

    public static FoodDataSource foodDatasource;
    public static  SleepDataSource sleepDatasource;
    public static  DiaperDataSource diaperDatasource;
    public static  ControlDataSource controlDatasource;
    public static String foodStr;
    public static String sleepStr;
    public static String diaperStr;
    public static List<Food> foods = new ArrayList<>();
    public static List<Sleep> sleeps = new ArrayList<>();
    public static List<Diaper> diapers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.options);
        imgLunch = (ImageView) findViewById(R.id.imageView);
        imgSleep = (ImageView) findViewById(R.id.imageView2);
        imgDiaper = (ImageView) findViewById(R.id.imageView3);
        imgControl = (ImageView) findViewById(R.id.imageView4);
        cal = (CalendarView) findViewById(R.id.calendarView2);


        foodDatasource = new FoodDataSource(this);
        sleepDatasource = new SleepDataSource(this);
        diaperDatasource = new DiaperDataSource(this);
        controlDatasource = new ControlDataSource(this);
        foodDatasource.open();
        sleepDatasource.open();
        diaperDatasource.open();
        controlDatasource.open();

        

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                String calDate=String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year);



                foodStr=getDBValues(foods, foodDatasource, calDate);
                sleepStr=getDBValues(sleeps, sleepDatasource, calDate);
                diaperStr=getDBValues(diapers, diaperDatasource, calDate);


                Intent k = new Intent(OptionsActivity.this, CalendarActivity.class);
                k.putExtra("DATE", calDate);
                k.putExtra("SLEEP", sleepStr);
                k.putExtra("STR", foodStr);
                k.putExtra("DIAPER", diaperStr);
                startActivity(k);
            }
        });

        imgLunch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(OptionsActivity.this, FoodActivity.class);
                startActivity(i);
                return true;
            }
        });

        imgSleep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(OptionsActivity.this, SleepActivity.class);
                startActivity(i);
                return true;
            }
        });

        imgDiaper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(OptionsActivity.this, DiaperActivity.class);
                startActivity(i);
                return true;
            }
        });

        imgControl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(OptionsActivity.this, ControlActivity.class);
                startActivity(i);
                return true;
            }
        });
    }


    public static String getDBValues(List<Diaper> diapers, DiaperDataSource dds, String calDate) {
        diaperStr="";
        diapers =dds.getDayDiapers(calDate);
        for (int i = 0; i < diapers.size(); i++) {
            diaperStr+=(diapers.get(i).getTime()+"    "+diapers.get(i).toString());
            if(i!=diapers.size()-1)
                diaperStr+=("\n");
        }
        return diaperStr;
    }

    public static String getDBValues(List<Sleep> sleeps, SleepDataSource sds, String calDate) {
        sleepStr="";
        sleeps =sds.getDaySleeps(calDate);
        for (int i = 0; i < sleeps.size(); i++) {
            sleepStr+=(sleeps.get(i).getTime()+"    "+sleeps.get(i).getDuration());
            if(i!=sleeps.size()-1)
                sleepStr+=("\n");
        }
        return sleepStr;
    }

    public static String getDBValues(List<Food> foods, FoodDataSource fds, String calDate) {
        foodStr="";
        foods=foodDatasource.getDayComments(calDate);
        for (int i = 0; i < foods.size(); i++) {
            foodStr+=(foods.get(i).getTime()+"   "+foods.get(i).getComment());
            if(i!=foods.size()-1)
                foodStr+=("\n");
        }
        return foodStr;
    }

}

