package com.money.expencetracker.dagger.component;

import android.content.Context;

import com.money.expencetracker.helpers.ActivityTransitionHelper;
import com.money.expencetracker.helpers.UIHelper;
import com.money.expencetracker.ui.activities.MainActivity;
import com.money.expencetracker.dagger.module.ExpanseApplicationModule;
import com.money.expencetracker.dagger.scope.ApplicationScope;
import com.money.expencetracker.manager.PermissionManager;
import com.money.expencetracker.ui.activities.BaseActivity;
import com.money.expencetracker.ui.activities.SplashActivity;
import com.money.expencetracker.ui.fragments.ReadSmsFragment;
import com.money.expencetracker.utilities.SharedPrefs;

import dagger.Component;

/**
 * Created by abhishek on 06/12/17.
 */
@ApplicationScope
@Component(modules = {ExpanseApplicationModule.class})
public interface ExpanseApplicationComponent {

    Context getApplicationContext();

    SharedPrefs getSharedPreference();

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(BaseActivity tutorialActivity);

    void inject(ReadSmsFragment smsFragment);

    PermissionManager getPermissionManager();

    UIHelper getUIHelper();

    ActivityTransitionHelper getActivityTransitionHelper();

}
