package com.money.expencetracker.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.money.expencetracker.ExpanseApplication;
import com.money.expencetracker.manager.PermissionManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by abhishek on 06/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    PermissionManager permissionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpanseApplication expanseApplication = (ExpanseApplication) getApplication();
        expanseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> unGrantedPermissions = new ArrayList<>();
        List<String> neverGrantedPermission = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    unGrantedPermissions.add(permission);
                } else {
                    neverGrantedPermission.add(permission);
                }
            }
        }
        if (unGrantedPermissions.size() > 0) {
            String[] requestPermission = unGrantedPermissions.toArray(new String[unGrantedPermissions.size()]);
            permissionManager.checkPermission(this, permissionManager.MULTIPLE_PERMISSION_REQUEST_CODE, requestPermission);
        }
        if (unGrantedPermissions.size() == 0 && neverGrantedPermission.size() == 0) {
            permissionGrantedDoYourOperation();
        }
    }

    abstract void permissionGrantedDoYourOperation();
}
