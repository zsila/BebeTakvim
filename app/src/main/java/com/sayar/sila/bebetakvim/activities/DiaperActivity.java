package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.adapters.DiaperListAdapter;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.HourUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.TypefaceUtil;
import com.sayar.sila.bebetakvim.database.DiaperDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zisan on 20.09.2016.
 */
public class DiaperActivity extends Activity {
    String diaperText = null;
    Diaper diaper = null;
    ListView diapers = null;
    private DiaperDataSource datasource;
    DiaperListAdapter customAdapter;
    List<Diaper> values = new ArrayList<>();
    String today = CalendarUtil.today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaper);

        diapers = (ListView) findViewById(R.id.listView3);
        final RadioButton wet = (RadioButton) findViewById(R.id.radioButton);
        final RadioButton dirty = (RadioButton) findViewById(R.id.radioButton2);
        ImageView ekle = (ImageView) findViewById(R.id.ekle2);
        TextView tarih = (TextView) findViewById(R.id.textView5);
        tarih.setText(today);

        dirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wet.setChecked(false);
            }
        });

        wet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dirty.setChecked(false);
            }
        });
        ekle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(wet.isChecked() || dirty.isChecked()){
                if (wet.isChecked()) {
                    diaperText = getString(R.string.diaper1);;
                }
                if (dirty.isChecked()) {
                    diaperText = getString(R.string.diaper2);
                }
                if (!diaperText.isEmpty())
                    diaper = datasource.createDiaper(diaperText,
                            HourUtil.getHM(CalendarUtil.getHour(), CalendarUtil.getMinute()),
                            //Integer.toString(CalendarUtil.getHour()) + ":" + Integer.toString(CalendarUtil.getMinute()),
                            //"30/9/2016");
                            Integer.toString(CalendarUtil.getDay()) + "/" + Integer.toString(CalendarUtil.getMonth()) + "/" + Integer.toString(CalendarUtil.getYear()));

                listDayDiapers();
                wet.setChecked(false);
                dirty.setChecked(false);
            }


            }
        });

        datasource = new DiaperDataSource(this);
        datasource.open();

        listDayDiapers();
    }


    public void listDayDiapers() {
        values = datasource.getDayDiapers(today);

        if (customAdapter == null) {
            customAdapter = new DiaperListAdapter(this, R.layout.lunch_line, values);
            diapers.setAdapter(customAdapter);
        } else {
            customAdapter.setValues(values);
        }
    }

}
