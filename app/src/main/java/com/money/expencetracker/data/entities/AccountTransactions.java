package com.money.expencetracker.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by abhishek on 20/12/17.
 */

public class AccountTransactions implements Parcelable {
    public static final String TABLE_NAME = "table_account";
    private int accountId;
    private String accountName;
    private String accountBalance;
    private Double timestamp;
    private String cardNo;
    private int transactionCount;

    protected AccountTransactions(Parcel in) {
        accountId = in.readInt();
        accountName = in.readString();
        accountBalance = in.readString();
        timestamp = in.readDouble();
        cardNo = in.readString();
        transactionCount = in.readInt();
    }

    public AccountTransactions() {
    }

    public static final Creator<AccountTransactions> CREATOR = new Creator<AccountTransactions>() {
        @Override
        public AccountTransactions createFromParcel(Parcel in) {
            return new AccountTransactions(in);
        }

        @Override
        public AccountTransactions[] newArray(int size) {
            return new AccountTransactions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accountId);
        dest.writeString(accountName);
        dest.writeString(accountBalance);
        dest.writeDouble(timestamp);
        dest.writeString(cardNo);
        dest.writeInt(transactionCount);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

}
