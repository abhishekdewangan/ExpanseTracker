package com.money.expencetracker.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.money.expencetracker.helpers.AppDatabaseHelper;

/**
 * Created by abhishek on 20/12/17.
 */

public class AppDatabaseCreator {

    private static AppDatabaseCreator appDatabaseCreator;
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();
    private AppDatabase appDatabase;
    private static final Object LOCK = new Object();

    private AppDatabaseCreator(Context context) {
        if (isDatabaseCreated == null || isDatabaseCreated.getValue() == null ||
                !isDatabaseCreated.getValue()) {
            createDb(context);
        }

    }

    public synchronized static AppDatabaseCreator getInstance(Context context) {
        if (appDatabaseCreator == null) {
            synchronized (LOCK) {
                if (appDatabaseCreator == null) {
                    appDatabaseCreator = new AppDatabaseCreator(context);
                }
            }
        }
        return appDatabaseCreator;
    }

    private void createDb(Context context) {
        isDatabaseCreated.postValue(false);

        appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                AppDatabaseHelper.DATABASE_NAME).addMigrations(RoomMigrations.MIGRATION_1_2).build();

        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> isDatabaseCreated() {
        return isDatabaseCreated;
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }

}
