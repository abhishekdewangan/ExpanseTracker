package com.money.expencetracker.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.money.expencetracker.ui.activities.AccountsActivity;
import com.money.expencetracker.ui.activities.ReadSmsActivity;

/**
 * Created by abhishek on 13/01/18.
 */

public class ActivityTransitionHelper {

    public ActivityTransitionHelper() {

    }

    public void startReadSmsActivity(Activity activity, Bundle bundle, boolean shouldCloseActivity) {
        Intent intent = new Intent(activity, ReadSmsActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(activity, intent, shouldCloseActivity);
    }

    public void startAccountsActivity(Activity activity, Bundle bundle, boolean shouldCloseActivity) {
        Intent intent = new Intent(activity, AccountsActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(activity, intent, shouldCloseActivity);
    }

    private void startActivity(Activity activity, Intent intent, boolean shouldCloseActivity) {
        activity.startActivity(intent);
        if (shouldCloseActivity) {
            activity.finish();
        }
    }
}
