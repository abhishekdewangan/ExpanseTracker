package com.money.expencetracker.data.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.money.expencetracker.async.ReadSmsAsyncTask;
import com.money.expencetracker.interfaces.AsyncTaskCallback;

/**
 * Created by abhishek on 10/12/17.
 */

public class SmsCountViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> smsCountLiveData;
    private MutableLiveData<Integer> totalSmsCountLiveData;
    private ReadSmsAsyncTask readSmsAsyncTask;

    public SmsCountViewModel(Application application) {
        super(application);
        smsCountLiveData = new MutableLiveData<>();
        smsCountLiveData.setValue(0);
        totalSmsCountLiveData = new MutableLiveData<>();
        totalSmsCountLiveData.setValue(0);
    }

    public LiveData<Integer> getSmsLiveData() {
        return smsCountLiveData;
    }

    public LiveData<Integer> getTotalSmsCount() {
        return totalSmsCountLiveData;
    }

    public void stopReadSmsTask() {
        if (readSmsAsyncTask != null) {
            readSmsAsyncTask.cancel(true);
            readSmsAsyncTask = null;
        }
    }

    public void startReadSmsTask() {
        if (readSmsAsyncTask != null) {
            readSmsAsyncTask.cancel(true);
            readSmsAsyncTask = null;
        }
        readSmsAsyncTask = new ReadSmsAsyncTask(this.getApplication());
        readSmsAsyncTask.setAsyncTaskCallback(new AsyncTaskCallback<Integer, Void>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onProgressUpdate(Integer[] progress) {
                if (progress != null && progress.length > 1) {
                    int flag1 = progress[0];
                    if (flag1 == 0) {
                        int smsCount = progress[1];
                        totalSmsCountLiveData.postValue(smsCount);
                        int smsReadCount = progress[2];
                        smsCountLiveData.postValue(smsReadCount);
                    } else {
                        int smsReadCount = progress[1];
                        smsCountLiveData.postValue(smsReadCount);
                    }
                }
            }

            @Override
            public void onPostExecute(Void result) {

            }
        });
        readSmsAsyncTask.execute();
    }

}
