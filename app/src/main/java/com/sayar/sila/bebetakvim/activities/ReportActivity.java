package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;

/**
 * Created by zisan on 8.10.2016.
 */
public class ReportActivity extends Activity {
    String year = Integer.toString(CalendarUtil.getYear());
    public static String FONT = "fonts/Amatic-Bold.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        ImageView boy = (ImageView) findViewById(R.id.imageBoy);
        ImageView kilo = (ImageView) findViewById(R.id.imageKilo);
        final TextView boytxt = (TextView) findViewById(R.id.textBoy);
        final TextView kilotxt = (TextView) findViewById(R.id.textKilo);
        TextView tarih = (TextView) findViewById(R.id.textDate);
        final TextView not = (TextView) findViewById(R.id.textNot);

        String ay=null;
        Bundle extras = getIntent().getExtras();
        if(!extras.getString("ay").isEmpty()) {
            ay = extras.getString("ay");
        }
        if (extras != null) {
            String boyStr = extras.getString("boy");
            String kiloStr = extras.getString("kilo");
            String notStr = extras.getString("not");

            boytxt.setText(boyStr);
            setFont(boytxt);
            kilotxt.setText(kiloStr);
            setFont(kilotxt);
            not.setText(notStr);
            setFont(not);
            tarih.setText(ay+" "+year);

            //The key argument here must match that used in the other activity
        }
    }

    public void setFont(TextView txt) {
        Typeface type = Typeface.createFromAsset(getAssets(), FONT);
        txt.setTypeface(type);
        txt.setTextColor(getResources().getColor(R.color.siyah));
    }
}
