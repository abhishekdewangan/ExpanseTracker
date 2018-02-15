package com.money.expencetracker;

import android.app.Application;
import android.os.AsyncTask;

import com.facebook.stetho.Stetho;
import com.money.expencetracker.dagger.component.DaggerExpanseApplicationComponent;
import com.money.expencetracker.dagger.component.ExpanseApplicationComponent;
import com.money.expencetracker.dagger.module.ExpanseApplicationModule;
import com.money.expencetracker.data.db.AppDatabaseCreator;

/**
 * Created by abhishek on 05/12/17.
 */

public class ExpanseApplication extends Application {
    private ExpanseApplicationComponent expanseApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        expanseApplicationComponent = DaggerExpanseApplicationComponent.builder().
                expanseApplicationModule(new ExpanseApplicationModule(this)).build();
        Stetho.initializeWithDefaults(this);
        CreateDbTask createDbTask = new CreateDbTask();
        createDbTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExpanseApplicationComponent getApplicationComponent() {
        return expanseApplicationComponent;
    }

    public class CreateDbTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabaseCreator.getInstance(getApplicationContext());
            return null;
        }
    }
}
