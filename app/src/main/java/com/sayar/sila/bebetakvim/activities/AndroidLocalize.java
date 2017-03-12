package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.database.LanguageDataSource;
import com.sayar.sila.bebetakvim.units.Language;


import java.util.Locale;

/**
 * Created by zisan on 17.11.2016.
 */
public class AndroidLocalize extends Activity {
    private LanguageDataSource datasource;
    Language lang;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localize);

        TextView ln = (TextView) findViewById(R.id.textView1);
        final CheckedTextView ctxt1 = (CheckedTextView) findViewById(R.id.checkedTextView);
        final CheckedTextView ctxt2 = (CheckedTextView) findViewById(R.id.checkedTextView2);

        datasource = new LanguageDataSource(this);
        datasource.open();


        if (datasource.isEmpty()) {
            ctxt1.setVisibility(View.VISIBLE);
            ctxt2.setVisibility(View.VISIBLE);
            ln.setVisibility(View.VISIBLE);
            ctxt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lang = datasource.createLanguage("tr");
                    setLocale(lang);
                    Toast.makeText(getApplication(), "Locale in Turkish !", Toast.LENGTH_LONG).show();
                }
            });

            ctxt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lang = datasource.createLanguage("en");
                    setLocale(lang);
                    Toast.makeText(getApplication(), "Locale in English !", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            lang = datasource.getLanguage();
           setLocale(lang);
        }
    }


    public void setLocale(Language lang) {
        Locale locale;
        if(lang.equals("tr")) {
            locale = new Locale(null);
        } else {
            locale = new Locale(lang.getLang());
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        Intent i = new Intent(AndroidLocalize.this, OptionsActivity.class);
        startActivity(i);
    }
}
