package com.money.expencetracker.data.viewmodels;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.money.expencetracker.data.entities.AccountTransactions;
import com.money.expencetracker.data.entities.Transaction;

import java.util.List;

/**
 * Created by abhishek on 13/01/18.
 */

public class TransactionsViewModel extends BaseViewModel {

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Transaction>> getTransactions(final String accountId) {
        LiveData<List<Transaction>> transactionsLiveData = Transformations.switchMap(databaseCreated, new Function<Boolean, LiveData<List<Transaction>>>() {
            @Override
            public LiveData<List<Transaction>> apply(Boolean isDbCreated) {
                if (!Boolean.TRUE.equals(isDbCreated)) {
                    return ABSENT;
                } else {
                    return databaseCreator.getDatabase().transactionsDao().getTransactionsByAccountName(accountId);
                }
            }
        });
        return transactionsLiveData;
    }
}
