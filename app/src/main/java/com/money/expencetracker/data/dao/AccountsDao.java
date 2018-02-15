package com.money.expencetracker.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.money.expencetracker.data.entities.Account;
import com.money.expencetracker.data.entities.AccountTransactions;

import java.util.List;

@Dao
public interface AccountsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAccount(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAccounts(List<Account> accounts);

    @Delete
    public void deleteAccount(Account account);

    @Delete
    public void deleteAccounts(List<Account> accounts);

    @Query("SELECT * FROM table_account WHERE balance IS NOT NULL ORDER BY id ASC")
    public LiveData<List<Account>> getAccounts();

    @Query("SELECT table_account.id AS accountId, table_account.balance AS accountBalance, table_account.timestamp AS timestamp " +
            ",table_account.card_no AS cardNo, table_account.name AS accountName, Count(table_transactions.account_id) as transactionCount " +
            "FROM table_account INNER JOIN table_transactions WHERE " +
            "table_account.id = table_transactions.account_id AND table_transactions.amount IS NOT NULL GROUP BY table_transactions.account_id")
    public LiveData<List<AccountTransactions>> getAccountsWithTransactions();

    @Query(("select * from table_account where name = :accountName"))
    public Account getAccount(String accountName);
}
