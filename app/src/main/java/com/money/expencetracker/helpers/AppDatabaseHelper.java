package com.money.expencetracker.helpers;

/**
 * Created by abhishek on 16/12/17.
 */

public class AppDatabaseHelper {
    public static final String DATABASE_NAME = "expense_db";

    public static final int VER_CREATING_BASE_DATABASE = 1;
    public static final int VER_CHANGE_TRANSACTION_ID = 2;

    // The current database version
    public static final int CUR_DATABASE_VERSION = VER_CHANGE_TRANSACTION_ID;


}
