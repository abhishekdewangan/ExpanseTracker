package com.money.expencetracker.async;

import android.content.Context;
import android.os.AsyncTask;

import com.money.expencetracker.interfaces.AsyncTaskCallback;

import java.lang.ref.WeakReference;

/**
 * Created by abhishek on 13/01/18.
 */

public class BaseAsyncTask<A,B,C> extends AsyncTask<A,B,C> {

    protected WeakReference<Context> contextWeakReference;
    protected AsyncTaskCallback<B,C> asyncTaskCallback;

    public BaseAsyncTask(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    public BaseAsyncTask(Context context, AsyncTaskCallback<B,C> asyncTaskCallback) {
        this(context);
        this.asyncTaskCallback = asyncTaskCallback;
    }

    @Override
    protected void onPreExecute() {
        if (contextWeakReference.get() != null
                && asyncTaskCallback != null) {
            asyncTaskCallback.onPreExecute();

        }
    }

    @Override
    protected void onProgressUpdate(B[] values) {
        if (contextWeakReference.get() != null
                && asyncTaskCallback != null) {
            asyncTaskCallback.onProgressUpdate(values);

        }
    }

    @Override
    protected C doInBackground(A[] as) {
        return null;
    }

    @Override
    protected void onPostExecute(C c) {
        if (contextWeakReference.get() != null
                && asyncTaskCallback != null) {
            asyncTaskCallback.onPostExecute(c);
        }
    }
}
