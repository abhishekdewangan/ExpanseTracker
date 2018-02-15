package com.money.expencetracker.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.money.expencetracker.R;

import butterknife.ButterKnife;

/**
 * Created by abhishek on 13/01/18.
 */

public class ReadSmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_read_sms);
        ButterKnife.bind(this);
    }
}
