package com.money.expencetracker.data.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.money.expencetracker.data.db.AppDatabaseCreator;

/**
 * Created by abhishek on 13/01/18.
 */

public class BaseViewModel extends AndroidViewModel {

    protected final static MutableLiveData ABSENT = new MutableLiveData();
    static {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

     LiveData<Boolean> databaseCreated;
     AppDatabaseCreator databaseCreator;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        databaseCreator = AppDatabaseCreator.getInstance(this.getApplication());
        databaseCreated = databaseCreator.isDatabaseCreated();
    }
}
