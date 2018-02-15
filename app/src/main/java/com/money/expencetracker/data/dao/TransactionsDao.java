package com.money.expencetracker.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.money.expencetracker.data.entities.Transaction;

import java.util.List;

/**
 * Created by abhishek on 16/12/17.
 */

@Dao
public interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTransaction(Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTransactions(List<Transaction> transactions);

    @Delete
    public void deleteTransaction(Transaction transaction);

    @Delete
    public void deleteTransactions(List<Transaction> transactions);

    @Query("SELECT * FROM table_transactions WHERE account_id NOT NULL AND account_id = :accountId ORDER BY timestamp DESC")
    public LiveData<List<Transaction>> getTransactionsByAccountName(String accountId);

    @Query("SELECT * FROM table_transactions WHERE transaction_id = :transactionId")
    public Transaction getTransaction(String transactionId);

}
