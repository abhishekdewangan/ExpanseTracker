package com.money.expencetracker.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.util.Log;

import com.money.expencetracker.helpers.AppDatabaseHelper;

/**
 * Created by abhishek on 15/02/18.
 */

public class RoomMigrations {
    private static final String TAG = "RoomMigrations";
    public static final Migration MIGRATION_1_2 =
            new Migration(AppDatabaseHelper.VER_CREATING_BASE_DATABASE, AppDatabaseHelper.VER_CHANGE_TRANSACTION_ID) {
                @Override
                public void migrate(SupportSQLiteDatabase database) {
                    Log.e(TAG, "MIGRATE: 1 2");
                    database.execSQL("DROP TABLE IF EXISTS `table_transactions`");
                    database.execSQL("CREATE TABLE IF NOT EXISTS `table_transactions` (`transaction_id` TEXT NOT NULL, `account_id` INTEGER NOT NULL, `amount` TEXT, `message` TEXT, `transaction_type` TEXT, `transaction_category` TEXT, `timestamp` REAL, `merchant_name` TEXT, PRIMARY KEY(`transaction_id`))");
                }
            };

    private void madeTransactionIdAsString(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE table_contents ADD COLUMN topic_category_icon TEXT");
    }

}
