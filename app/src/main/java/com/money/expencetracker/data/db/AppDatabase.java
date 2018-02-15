package com.money.expencetracker.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.money.expencetracker.data.dao.AccountsDao;
import com.money.expencetracker.data.dao.TransactionsDao;
import com.money.expencetracker.data.entities.Account;
import com.money.expencetracker.data.entities.Transaction;
import com.money.expencetracker.helpers.AppDatabaseHelper;

/**
 * Created by abhishek on 16/12/17.
 */

@Database(entities = {Transaction.class, Account.class}, version = AppDatabaseHelper.CUR_DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TransactionsDao transactionsDao();

    public abstract AccountsDao accountsDao();

}
