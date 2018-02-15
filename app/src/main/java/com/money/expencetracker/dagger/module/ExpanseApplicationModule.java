package com.money.expencetracker.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.money.expencetracker.dagger.scope.ApplicationScope;
import com.money.expencetracker.helpers.ActivityTransitionHelper;
import com.money.expencetracker.helpers.UIHelper;
import com.money.expencetracker.manager.PermissionManager;
import com.money.expencetracker.utilities.SharedPrefs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishek on 06/12/17.
 */

@Module
public class ExpanseApplicationModule {
    private Context applicationContext;

    public ExpanseApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @ApplicationScope
    public Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @ApplicationScope
    public SharedPrefs provideSharedPreference() {
        return new SharedPrefs(applicationContext);
    }

    @Provides
    @ApplicationScope
    public PermissionManager providePermissionHelper() {
        return new PermissionManager(applicationContext);
    }

    @Provides
    @ApplicationScope
    public UIHelper provideUIHelper() {
        return new UIHelper();
    }

    @Provides
    @ApplicationScope
    public ActivityTransitionHelper provideActivityTransitionHelper(){
        return new ActivityTransitionHelper();
    }
}
