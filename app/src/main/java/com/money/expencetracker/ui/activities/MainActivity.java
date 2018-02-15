package com.money.expencetracker.ui.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.money.expencetracker.ExpanseApplication;
import com.money.expencetracker.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpanseApplication application = (ExpanseApplication) getApplication();
        application.getApplicationComponent().inject(this);
    }
}
