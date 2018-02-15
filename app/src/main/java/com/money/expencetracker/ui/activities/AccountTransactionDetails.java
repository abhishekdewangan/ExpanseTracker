package com.money.expencetracker.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.money.expencetracker.R;
import com.money.expencetracker.data.entities.AccountTransactions;
import com.money.expencetracker.data.entities.Transaction;
import com.money.expencetracker.data.viewmodels.TransactionsViewModel;
import com.money.expencetracker.helpers.AccountHelper;
import com.money.expencetracker.helpers.UIHelper;
import com.money.expencetracker.ui.adapters.TransactionsRecyclerAdapter;
import com.money.expencetracker.ui.behaviour.CommonItemDecorator;
import com.money.expencetracker.utilities.Statics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abhishek on 14/01/18.
 */

public class AccountTransactionDetails extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerTransaction)
    RecyclerView recyclerTransaction;

    @BindView(R.id.tvTotalBal)
    AppCompatTextView tvTotalBal;

    @BindView(R.id.tvTransactionsCount)
    AppCompatTextView tvTransactionsCount;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.imgAccount)
    AppCompatImageView imgAccount;

    @BindView(R.id.tv_toolbar_topic_title)
    AppCompatTextView tvToolbarTitle;

    private UIHelper uiHelper;

    private AccountTransactions account;

    private TransactionsViewModel transactionsViewModel;

    private TransactionsRecyclerAdapter transactionsRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_accout_transactions);
        ButterKnife.bind(this);

        initData();
        setupToolbar();
        setupHeader();
        setupTransactionRecyclerView();

        mAppBarLayout.addOnOffsetChangedListener(this);
        transactionsViewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);

        tvToolbarTitle.setText(getString(R.string.details));

        subscribeAccountTransactions();

    }

    private void initData() {
        uiHelper = new UIHelper();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            account = bundle.getParcelable(Statics.BUNDLE_ACCOUNT_OBJ);
        }
    }

    private void setupHeader() {
        if (account == null) return;
        tvTotalBal.setText(account.getAccountBalance());
        String noOfTransactions = account.getTransactionCount() + "  " + getString(R.string.transactions);
        tvTransactionsCount.setText(noOfTransactions);
        int srcAccount = AccountHelper.getBankImage(account.getAccountName());
        if (srcAccount != -1) {
            imgAccount.setImageResource(srcAccount);
        } else {
            imgAccount.setImageBitmap(null);
        }
    }

    private void setupToolbar() {
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupTransactionRecyclerView() {
        recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
        recyclerTransaction.addItemDecoration(new CommonItemDecorator());
        transactionsRecyclerAdapter = new TransactionsRecyclerAdapter(this);
        recyclerTransaction.setAdapter(transactionsRecyclerAdapter);
    }

    private void subscribeAccountTransactions() {
        transactionsViewModel.getTransactions(""+account.getAccountId()).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable List<Transaction> transactions) {
                transactionsRecyclerAdapter.setTransactions(transactions);
            }
        });
    }

    @Override
    public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {
        if (uiHelper.isActivityAlive(this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!(uiHelper.isActivityAlive(AccountTransactionDetails.this)
                            && appBarLayout != null)) {
                        return;
                    }
                    float verticalOffsetPercentage = Math.abs(verticalOffset) * 1.0f /
                            appBarLayout.getTotalScrollRange();
                    float actualVal = 1 - verticalOffsetPercentage;

                    if (actualVal > .9) {
                        tvTotalBal.setAlpha(1);
                        tvTransactionsCount.setAlpha(0.6f);
                    } else {
                        tvTotalBal.setAlpha(actualVal / 5);
                        tvTransactionsCount.setAlpha(actualVal / 6);
                    }
                }
            });
        }

    }
}
