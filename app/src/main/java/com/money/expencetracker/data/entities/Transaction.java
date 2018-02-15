package com.money.expencetracker.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by abhishek on 14/12/17.
 */

@Entity(tableName = Transaction.TABLE_NAME)
public class Transaction implements Parcelable {
    public static final String TABLE_NAME = "table_transactions";


    @ColumnInfo(name = ColumnNames.transactionId)
    @PrimaryKey
    @NonNull
    private String transactionId;

    @ColumnInfo(name = ColumnNames.accoutId)
    @NonNull
    private int accoutId;

    @ColumnInfo(name = ColumnNames.amount)
    private String amount;

    @ColumnInfo(name = ColumnNames.message)
    private String message;

    @ColumnInfo(name = ColumnNames.transactionType)
    private String transactionType;

    @ColumnInfo(name = ColumnNames.transactionCategory)
    private String transactionCategory;

    @ColumnInfo(name = ColumnNames.timestamp)
    private Double timeStamp;

    @ColumnInfo(name = ColumnNames.merchantName)
    private String merchantName;



    public Transaction(){

    }

    private Transaction(Parcel in) {
        transactionId = in.readString();
        accoutId = in.readInt();
        amount = in.readString();
        message = in.readString();
        transactionType = in.readString();
        transactionCategory = in.readString();
        timeStamp = in.readDouble();
        merchantName = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionId);
        dest.writeInt(accoutId);
        dest.writeString(amount);
        dest.writeString(message);
        dest.writeString(transactionType);
        dest.writeString(transactionCategory);
        dest.writeDouble(timeStamp);
        dest.writeString(merchantName);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccount() {
        return accoutId;
    }

    public void setAccount(int account) {
        this.accoutId = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public Double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @NonNull
    public int getAccoutId() {
        return accoutId;
    }

    public void setAccoutId(@NonNull int accoutId) {
        this.accoutId = accoutId;
    }

    public interface ColumnNames {
        String transactionId = "transaction_id";
        String accoutId = "account_id";
        String amount = "amount";
        String message = "message";
        String transactionType = "transaction_type";
        String transactionCategory = "transaction_category";
        String timestamp = "timestamp";
        String merchantName = "merchant_name";
    }
}
