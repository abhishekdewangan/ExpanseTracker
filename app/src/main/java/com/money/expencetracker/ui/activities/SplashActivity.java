package com.money.expencetracker.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.money.expencetracker.ExpanseApplication;
import com.money.expencetracker.R;
import com.money.expencetracker.async.ReadSmsAsyncTask;
import com.money.expencetracker.helpers.ActivityTransitionHelper;
import com.money.expencetracker.manager.PermissionManager;
import com.money.expencetracker.utilities.SharedPrefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abhishek on 06/12/17.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.viewGetStarted)
    View viewGetStarted;

    @Inject
    PermissionManager permissionManager;

    @Inject
    ActivityTransitionHelper activityTransitionHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_screen);

        // binding view to butter knife
        ButterKnife.bind(this);

        // injecting activity to use application modules
        ExpanseApplication expanseApplication = (ExpanseApplication) getApplication();
        expanseApplication.getApplicationComponent().inject(this);
    }

    @OnClick(R.id.viewGetStarted)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewGetStarted :
                String[] smsPermission = {Manifest.permission.READ_SMS , Manifest.permission.CALL_PHONE};
                if (permissionManager.checkPermission(SplashActivity.this, permissionManager.MULTIPLE_PERMISSION_REQUEST_CODE, smsPermission)) {
                    permissionGrantedDoYourOperation();
                }
                break;
        }
    }

    @Override
    void permissionGrantedDoYourOperation() {
        activityTransitionHelper.startReadSmsActivity(this, null, true);
    }

}
