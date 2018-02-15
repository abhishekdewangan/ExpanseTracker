package com.money.expencetracker.helpers;

import com.money.expencetracker.R;

/**
 * Created by abhishek on 14/01/18.
 */

public class AccountHelper {

    public static int getBankImage(String bankName) {
        switch (bankName) {
            case "ICICI Credit Card":
            case "ICICI":
                return R.mipmap.icici;
            case "HDFC":
                return R.mipmap.hdfc;
            case "Sodexo":
                return R.mipmap.sodexo;
            default:
                return -1;
        }
    }

}
