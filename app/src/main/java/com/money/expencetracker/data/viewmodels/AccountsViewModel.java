package com.money.expencetracker.data.viewmodels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.money.expencetracker.data.entities.Account;
import com.money.expencetracker.data.entities.AccountTransactions;

import java.util.List;

/**
 * Created by abhishek on 13/01/18.
 */

public class AccountsViewModel extends BaseViewModel {

    public AccountsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<AccountTransactions>> getAccountsList() {
        LiveData<List<AccountTransactions>> accountsLiveData = Transformations.switchMap(databaseCreated, new Function<Boolean, LiveData<List<AccountTransactions>>>() {
            @Override
            public LiveData<List<AccountTransactions>> apply(Boolean isDbCreated) {
                if (!Boolean.TRUE.equals(isDbCreated)) {
                    return ABSENT;
                } else {
                    return databaseCreator.getDatabase().accountsDao().getAccountsWithTransactions();
                }
            }
        });
        return accountsLiveData;
    }
}
