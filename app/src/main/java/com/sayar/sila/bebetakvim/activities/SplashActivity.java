package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.TypefaceUtil;


/**
 * Created by zisan on 25.10.2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
        finish();
    }

}
