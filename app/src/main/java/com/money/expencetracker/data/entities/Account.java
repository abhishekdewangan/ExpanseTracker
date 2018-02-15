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
@Entity(tableName = Account.TABLE_NAME)
public class Account implements Parcelable {
    public static final String TABLE_NAME = "table_account";

    @ColumnInfo(name = ColumnNames.id)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int accountId;

    @ColumnInfo(name = ColumnNames.name)
    @NonNull
    private String accountName;

    @ColumnInfo(name = ColumnNames.balance)
    private String accountBalance;

    @ColumnInfo(name = ColumnNames.timestamp)
    private Double timestamp;

    @ColumnInfo(name = ColumnNames.cardNo)
    private String cardNo;

    protected Account(Parcel in) {
        accountId = in.readInt();
        accountName = in.readString();
        accountBalance = in.readString();
        timestamp = in.readDouble();
        cardNo = in.readString();
    }

    public Account() {
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
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

    public interface ColumnNames {
        String id = "id";
        String name = "name";
        String type = "account_type";
        String balance = "balance";
        String timestamp = "timestamp";
        String cardNo = "card_no";
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
