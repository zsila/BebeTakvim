package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.DialogUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.HourUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.ControlDataSource;
import com.sayar.sila.bebetakvim.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zisan on 22.09.2016.
 */
public class ControlActivity extends Activity implements NumberPicker.OnValueChangeListener {
    Control control = null;

    private ControlDataSource datasource;
    List<Control> values = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //when start activty, keyboard is hidden until click on edittext

        ImageView boy = (ImageView) findViewById(R.id.imageView5);
        ImageView kilo = (ImageView) findViewById(R.id.imageView7);
        final TextView boytxt = (TextView) findViewById(R.id.textView10);
        final TextView kilotxt = (TextView) findViewById(R.id.textView11);
        TextView tarih = (TextView) findViewById(R.id.textView9);
        ImageView kaydet = (ImageView) findViewById(R.id.imageNot);
        ImageView health = (ImageView) findViewById(R.id.imageView14);
        final EditText not = (EditText) findViewById(R.id.editText4);


        tarih.setText(CalendarUtil.today);
        setFont(tarih, boytxt, kilotxt, not);

        datasource = new ControlDataSource(this);
        datasource.open();

        values = datasource.getAllControls();

        for (Control v : values) {

            if (CalendarUtil.extractMonth(v.getDate()).equals(CalendarUtil.extractMonth(CalendarUtil.today))) {
                boytxt.setText(v.getBoy());
                kilotxt.setText(v.getKilo());
                not.setText(v.getNot());
                i++;
            }
        }



        final Activity thisAct = this;
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showCm(boytxt, "Boy", 40, 150, "cm", thisAct);
            }
        });
        kilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showDialog(kilotxt, "Kilo", 1, 30, 0, 9, "kg", thisAct);
            }
        });
        kaydet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (not.getText() != null || boytxt.getText() != null || kilotxt.getText() != null) {
                    if (i > 0) {
                        //if(!boytxt.getText().toString().isEmpty() || !kilotxt.getText().toString().isEmpty() || !not.getText().toString().isEmpty()) {
                        for (Control c : values) {
                            if (CalendarUtil.extractMonth(c.getDate()).equals(CalendarUtil.extractMonth(CalendarUtil.today))) {
                                ContentValues cv = new ContentValues();
                                cv.put(DatabaseHelper.COLUMN_NOT, not.getText().toString());
                                cv.put(DatabaseHelper.COLUMN_BOY, boytxt.getText().toString());
                                cv.put(DatabaseHelper.COLUMN_KILO, kilotxt.getText().toString());
                                ControlDataSource.database.update(DatabaseHelper.TABLE_CONTROL, cv, "_id=" + c.getId(), null);
                            }
                        }
                    } else if (i == 0) {
                        control = datasource.createControl(boytxt.getText().toString(), kilotxt.getText().toString(), not.getText().toString(),
                                HourUtil.getHM(CalendarUtil.getHour(), CalendarUtil.getMinute()),
                                CalendarUtil.today);
                    }

                    Toast.makeText(getApplicationContext(), getString(R.string.record),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.verigir),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        not.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ControlActivity.this, HealthActivity.class);
                startActivity(i);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }

    public void setFont(TextView... txt) {
        Typeface type = Typeface.createFromAsset(getAssets(), Util.font);
        for (int i = 0; i < txt.length; i++) {
            txt[i].setTypeface(type);
        }
    }

}
