package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.adapters.FoodListAdapter;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.HourUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.FoodDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by zisan on 9.08.2016.
 */
public class FoodActivity extends Activity {
    String item = "";
    private FoodDataSource datasource;
    FoodListAdapter customAdapter;
    ListView listView = null;
    List<Food> values = new ArrayList<>();

    public static String FONT = "fonts/Existence-Light.otf";


    String today = CalendarUtil.today;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch);

        listView = (ListView) findViewById(R.id.listView);
        final AutoCompleteTextView ATView = (AutoCompleteTextView) findViewById(R.id.textView2);
        TextView tarih = (TextView) findViewById(R.id.textView7);
        ImageView buttonAdd = (ImageView) findViewById(R.id.ekle);

        tarih.setText(today);
        setFont(tarih, ATView);



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // save the new food to the database
                if (!item.equals(null)) {
                    item = ATView.getText().toString();
                    if (!item.isEmpty()) {
                        datasource.createComment(item,
                                HourUtil.getHM(CalendarUtil.getHour(), CalendarUtil.getMinute()),
                                //Integer.toString(CalendarUtil.getHour()) + ":" + Integer.toString(CalendarUtil.getMinute()),
                                Integer.toString(CalendarUtil.getDay()) + "/" + Integer.toString(CalendarUtil.getMonth()) + "/" + Integer.toString(CalendarUtil.getYear()));
                    }

                    listDayComments(today);
                    ATView.setText("");
                }
            }
        });

        ATView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.textView2);
        String[] foods = getResources().getStringArray(R.array.food_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        textView.setAdapter(adapter);

        datasource = new FoodDataSource(this);
        datasource.open();

        listDayComments(today);

    }




    public void listDayComments(String date) {
        values = datasource.getDayComments(date);

        if (customAdapter == null) {
            customAdapter = new FoodListAdapter(this, R.layout.lunch_line, values);

            if (listView != null)
                listView.setAdapter(customAdapter);
        } else {
            customAdapter.setValues(values);
        }
    }
    public void setFont(TextView... txt) {
        Typeface type = Typeface.createFromAsset(getAssets(), Util.font);
        for (int i = 0; i < txt.length; i++) {
            txt[i].setTypeface(type);
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
