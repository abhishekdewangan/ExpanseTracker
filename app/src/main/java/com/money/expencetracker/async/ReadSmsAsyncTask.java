package com.money.expencetracker.async;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import com.money.expencetracker.data.db.AppDatabase;
import com.money.expencetracker.data.db.AppDatabaseCreator;
import com.money.expencetracker.data.entities.Account;
import com.money.expencetracker.data.entities.Transaction;
import com.money.expencetracker.helpers.SmsReadHelper;
import com.money.expencetracker.interfaces.AsyncTaskCallback;
import com.money.expencetracker.utilities.SharedPrefs;

/**
 * Created by abhishek on 16/12/17.
 */

public class ReadSmsAsyncTask extends BaseAsyncTask<Void, Integer, Void> {

    private final static  String TAG = ReadSmsAsyncTask.class.getName();
    private int lastIndex;
    SharedPrefs sharedPrefs;
    private int lastSmsId = 0;
    private SmsReadHelper smsReadHelper;
    private int smsReadCount = 0;

    public ReadSmsAsyncTask(Context context) {
        super(context);
        sharedPrefs = new SharedPrefs(context);
    }

    public void setAsyncTaskCallback(AsyncTaskCallback<Integer, Void> asyncTaskCallback) {
        this.asyncTaskCallback = asyncTaskCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        lastSmsId = sharedPrefs.getInt(SharedPrefs.LAST_SMS_ID);
        smsReadHelper = new SmsReadHelper();
        smsReadCount  = sharedPrefs.getInt(SharedPrefs.SMS_READ_COUNT);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (contextWeakReference.get() == null) return null;

        // setting up sms uri and getting sms cursor
        Uri uri = Uri.parse("content://sms");
        ContentResolver cr = contextWeakReference.get().getContentResolver();
        String where = "";
        int size = lastSmsId > 1 ? 2 : 1;
        String[] args = new String[size];
        args[0] = "1";
        if (lastSmsId > 1) {
            where = "type=? AND _id < ?";
            args[1] = "" + lastSmsId;
        } else {
            where = "type=?";
        }

        Cursor cursor = cr.query(uri, null, where, args, "date  DESC");
        if (cursor == null || cursor.getCount() == 0) return null;

        //App Database
        AppDatabase appDatabase = AppDatabaseCreator.getInstance(contextWeakReference.get()).getDatabase();

        // updating total sms smsReadCount counter
        int totalSmsCount = cursor.getCount();
        if (sharedPrefs.getInt(SharedPrefs.TOTAL_SMS_COUNT) <= 0) {
            sharedPrefs.putInt(SharedPrefs.TOTAL_SMS_COUNT, totalSmsCount);
        }
        Integer[] arr = {0, sharedPrefs.getInt(SharedPrefs.TOTAL_SMS_COUNT), smsReadCount};
        onProgressUpdate(arr);
        while (cursor.moveToNext() ) {
            smsReadCount++;
            // columnId, body ,address, date
            String id = cursor.getString(cursor.getColumnIndex(Telephony.Sms._ID));
            String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
            String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
            Double dateInMs = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE)));
            // if sms body contains otp then continue without doing any operation
            if (smsReadHelper.isOtpMessage(body)) continue;

            // checking account is already there or not if not create account object
            String accountName = smsReadHelper.getAccountName(address, body);
            if (!TextUtils.isEmpty(accountName)) {
                Account account = appDatabase.accountsDao().getAccount(accountName);
                if (account == null) {
                    account = new Account();
                    account.setAccountName(accountName);
                }
                // checking for card no if any
                if (TextUtils.isEmpty(account.getCardNo())) {
                    String cardNo = smsReadHelper.getAccountCardNo(body);
                    if (!TextUtils.isEmpty(cardNo)) {
                        account.setCardNo(cardNo);
                    }
                }
                smsReadHelper.buildAccountObject(account, body, dateInMs);

                //inserting account to database
                appDatabase.accountsDao().insertAccount(account);

                // getting updated account after inserting
                account = appDatabase.accountsDao().getAccount(accountName);

                // create transaction object
                Transaction transaction = appDatabase.transactionsDao().getTransaction(id);
                if (transaction == null) {
                    transaction = new Transaction();
                }
                transaction.setAccount(account.getAccountId());
                transaction.setTimeStamp(dateInMs);
                transaction.setTransactionId(id);
                smsReadHelper.buildTransactionObject(transaction, body);

                // inserting transaction object to database
                if (!TextUtils.isEmpty(transaction.getAmount()) && transaction.getAccount() > 0 && !TextUtils.isEmpty(transaction.getTransactionType())) {
                    appDatabase.transactionsDao().insertTransaction(transaction);
                }
            }


            lastIndex = Integer.parseInt(id);
            arr[0] = 1;
            arr[1] = smsReadCount;
            onProgressUpdate(arr);
            sharedPrefs.putInt(SharedPrefs.SMS_READ_COUNT, smsReadCount);
            sharedPrefs.putInt(SharedPrefs.LAST_SMS_ID, lastIndex);
        }

        cursor.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        Log.e("onTaskCancelled"," Called");
        super.onCancelled();
    }
}
