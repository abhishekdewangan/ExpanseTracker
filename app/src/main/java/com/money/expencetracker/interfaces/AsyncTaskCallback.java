package com.money.expencetracker.interfaces;

/**
 * Created by abhishek on 16/12/17.
 */

public interface AsyncTaskCallback<A,B> {

    public void onPreExecute();

    public void onProgressUpdate(A[] progress);

    public void onPostExecute(B result);
}
