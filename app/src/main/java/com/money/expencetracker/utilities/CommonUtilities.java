package com.money.expencetracker.utilities;

import android.text.format.DateFormat;

/**
 * Created by abhishek on 13/01/18.
 */

public class CommonUtilities {

    public static String convertDate(Long dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, dateInMilliseconds).toString();
    }

    public static boolean isStringNotNull(String str) {
        if (str != null && str.length() > 0)
            return true;
        return false;
    }
}
