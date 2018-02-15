package com.money.expencetracker.manager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.money.expencetracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek on 06/12/17.
 */

public class PermissionManager {
    public final int SMS_READ_PERMISSION_REQUEST_CODE = 1;
    public final int MULTIPLE_PERMISSION_REQUEST_CODE = 2;

    public Context context;

    private  Map<String, String> mPermissionTitleMap = new HashMap<>();

    public PermissionManager(Context context) {
        mPermissionTitleMap.put(Manifest.permission.READ_SMS, "SMS PERMISSION");
        this.context = context;
    }

    public boolean checkPermission(Activity activity, int permissionRequestCode, String[] permissions) {
        boolean isAllPermissionGranted;
        List<String> unGrantedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                    unGrantedPermissions.add(permission);
            }
        }
        if (unGrantedPermissions.size() > 0) {
            isAllPermissionGranted = false;
            String[] requestingPermissions = unGrantedPermissions.toArray(new String[unGrantedPermissions.size()]);
            ActivityCompat.requestPermissions(activity, requestingPermissions, permissionRequestCode);
        } else {
            isAllPermissionGranted = true;
        }

        return isAllPermissionGranted;
    }

    private boolean isPermissionGranted(String permission) {
        boolean isPermissionGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isPermissionGranted = ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return isPermissionGranted;
    }

    public boolean shouldShowPermissionRequestReasonDialog(Activity activity, String permission) {
        boolean isPermissionReasonNeedToShow;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isPermissionReasonNeedToShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        } else {
            isPermissionReasonNeedToShow = false;
        }
        return isPermissionReasonNeedToShow;
    }

    public void showPermissionDialog(String permission, Activity activity) {
        StringBuilder permissionMessageBuilder = new StringBuilder();
        switch (permission) {
            case Manifest.permission.READ_SMS:
                permissionMessageBuilder.append(context.getString(R.string.sms_read_message));
                break;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle(mPermissionTitleMap.get(permission));
        dialogBuilder.setMessage(permissionMessageBuilder.toString());
        dialogBuilder.show();
    }
}

