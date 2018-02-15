package com.money.expencetracker.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.money.expencetracker.ExpanseApplication;
import com.money.expencetracker.R;
import com.money.expencetracker.data.viewmodels.SmsCountViewModel;
import com.money.expencetracker.ui.activities.AccountsActivity;
import com.money.expencetracker.utilities.SharedPrefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abhishek on 13/01/18.
 */

public class ReadSmsFragment extends Fragment {

    private SmsCountViewModel smsCountViewModel;
    @BindView(R.id.progressReadSms)
    ProgressBar smsProgress;
    @BindView(R.id.tvSmsReadCount)
    AppCompatTextView tvSmsReadCount;
    private int totalSmsCount = 0;
    @Inject
    SharedPrefs sharedPrefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_sms, null, false);

        ButterKnife.bind(this, view);
        // injecting activity to use application modules
        ExpanseApplication expanseApplication = (ExpanseApplication) getActivity().getApplication();
        expanseApplication.getApplicationComponent().inject(this);

        smsCountViewModel = ViewModelProviders.of(this).get(SmsCountViewModel.class);
        subscribeToReadSmsCount();
        subscribeToTotalSmsCount();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        smsCountViewModel.startReadSmsTask();
    }

    private void subscribeToReadSmsCount() {
        smsCountViewModel.getSmsLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer smsCount) {
                if (smsCount != null) {
                    updateProgress(smsCount);
                }
            }
        });
    }

    private void subscribeToTotalSmsCount() {
        smsCountViewModel.getTotalSmsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer smsCount) {
                if (smsCount != null) {
                    smsProgress.setMax(smsCount);
                    totalSmsCount = smsCount;
                }
            }
        });
    }

    private void updateProgress(int count) {
        if (totalSmsCount > 0) {
            smsProgress.setProgress(count);
            String smsCountString = count + "/" + totalSmsCount;
            tvSmsReadCount.setText(smsCountString);
            if (count >= totalSmsCount) {
                Intent intent = new Intent(getActivity(), AccountsActivity.class);
                startActivity(intent);
                sharedPrefs.putInt(SharedPrefs.TOTAL_SMS_COUNT, 0);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        smsCountViewModel.stopReadSmsTask();
    }
}
