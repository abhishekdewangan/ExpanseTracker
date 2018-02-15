package com.money.expencetracker.helpers;

import android.app.Activity;

/**
 * Created by abhishek on 13/01/18.
 */

public class UIHelper {

    public UIHelper() {

    }

    public boolean isActivityAlive(Activity activity) {
        return activity != null && !activity.isFinishing();
    }


}
