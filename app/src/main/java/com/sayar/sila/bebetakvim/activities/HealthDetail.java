package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.TypefaceUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;

/**
 * Created by zisan on 1.11.2016.
 */
public class HealthDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthmonth);

        TextView not = (TextView) findViewById(R.id.textView14);
        Intent i =getIntent();
        String str = i.getStringExtra("NOT");

        not.setText(str);

        setFont(not);

    }

    public void setFont(TextView... txt) {
        Typeface type = Typeface.createFromAsset(getAssets(), Util.font);
        for (int i = 0; i < txt.length; i++) {
            txt[i].setTypeface(type);
        }
    }
}
