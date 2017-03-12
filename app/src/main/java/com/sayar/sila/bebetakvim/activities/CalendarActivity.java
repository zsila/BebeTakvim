package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.ControlDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zisan on 8.08.2016.
 */
public class CalendarActivity extends Activity implements SimpleGestureFilter.SimpleGestureListener {
    private SimpleGestureFilter detector;

    public static String FONT = "fonts/Existence-Light.otf";

    Control control = null;
    private ControlDataSource datasource;
    List<Control> values = new ArrayList<>();

    String boyStr;
    String kiloStr;
    String notStr;
    String tarihStr;
    String month="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        detector = new SimpleGestureFilter(this, this);

        LinearLayout screen = (LinearLayout) findViewById(R.id.layout);

        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout);
        //TextView sumSleep = (TextView) findViewById(R.id.editText5);
        //TextView sumDiaper = (TextView) findViewById(R.id.editText6);
        final TextView tarih = (TextView) findViewById(R.id.textView6);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String date = extras.getString("DATE");
            String str = extras.getString("STR");
            String sleep = extras.getString("SLEEP");
            String diaper = extras.getString("DIAPER");

            tarihStr = date;
            tarih.setText(tarihStr);
            setFont(tarih);
            if (!str.isEmpty()) {
                TextView summary = new TextView(this);
                setTextview(summary, str, R.drawable.borderblue, R.drawable.bbrn, 50);
                summary.setPadding(14, 5, 0, 5);
                ll.addView(summary);
            }
            if (!sleep.isEmpty()) {
                TextView sumSleep = new TextView(this);
                setTextview(sumSleep, sleep, R.drawable.borderyellow, R.drawable.bebeto, 30);
                sumSleep.setPadding(14, 5, 0, 5);
                ll.addView(sumSleep);
            }
            if (!diaper.isEmpty()) {
                TextView sumDiaper = new TextView(this);
                setTextview(sumDiaper, diaper, R.drawable.bordergreen, R.drawable.bz, 30);
                sumDiaper.setPadding(14, 5, 0, 5);
                ll.addView(sumDiaper);
            }
        }

        datasource = new ControlDataSource(this);
        datasource.open();

        values = datasource.getAllControls();

        for (Control v : values) {
            if (CalendarUtil.extractMonth(v.getDate()).equals(CalendarUtil.extractMonth(tarihStr))) {
                boyStr = v.getBoy();
                kiloStr = v.getKilo();
                notStr = v.getNot();
            }
        }

        month = CalendarUtil.getMonthName(tarihStr);

    }

    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }


    private void setTextview(TextView txt, String str, int img, int leftImg, int drawPadding) {
        txt.setBackgroundResource(img);
        txt.setCompoundDrawablesWithIntrinsicBounds(leftImg, 0, 0, 0);
        txt.setCompoundDrawablePadding(drawPadding);
        txt.setClickable(false);
        txt.setText(str);
        txt.setTextSize(15);
        setFont(txt);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, 10);
        txt.setLayoutParams(llp);
    }

    public void setFont(TextView... txt) {
        Typeface type = Typeface.createFromAsset(getAssets(), Util.font);
        for (int i = 0; i < txt.length; i++) {
            txt[i].setTypeface(type);
        }
    }


    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                callDay("yesterday");

            case SimpleGestureFilter.SWIPE_LEFT:
                callDay("tomorrow");
        }
    }

    private void callDay(String t) {
        String newDate;//="25/10/2016";
        Date date = null;
        String y = CalendarUtil.extractYear(tarihStr);
        String m = CalendarUtil.extractMonth(tarihStr);
        String d = CalendarUtil.extractDay(tarihStr);

        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        try {
            date = format.parse(d + "/" + m + "/" + y);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date day = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (t.equals("yesterday")) {
            cal.add(Calendar.DATE, -1);
            day = cal.getTime();
        } else if (t.equals("tomorrow")) {
            cal.add(Calendar.DATE, +1);
            day = cal.getTime();
        }

        newDate = format.format(day);
        //newDate = d + "/" + m + "/" + y;

        String str = OptionsActivity.getDBValues(OptionsActivity.foods, OptionsActivity.foodDatasource, newDate);
        String sleep = OptionsActivity.getDBValues(OptionsActivity.sleeps, OptionsActivity.sleepDatasource, newDate);
        String diaper = OptionsActivity.getDBValues(OptionsActivity.diapers, OptionsActivity.diaperDatasource, newDate);


        Intent k = getIntent();
        k.putExtra("DATE", newDate);
        k.putExtra("SLEEP", sleep);
        k.putExtra("STR", str);
        k.putExtra("DIAPER", diaper);
        finish();
        startActivity(k);
    }



    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();

    }
}