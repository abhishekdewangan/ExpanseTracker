package com.money.expencetracker.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.money.expencetracker.R;
import com.money.expencetracker.data.entities.AccountTransactions;
import com.money.expencetracker.data.viewmodels.AccountsViewModel;
import com.money.expencetracker.helpers.UIHelper;
import com.money.expencetracker.ui.adapters.AccountsRecyclerAdapter;
import com.money.expencetracker.ui.behaviour.CommonItemDecorator;
import com.money.expencetracker.utilities.Statics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abhishek on 13/01/18.
 */

public class AccountsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.recyclerAccounts)
    RecyclerView recyclerAccounts;

    @BindView(R.id.tvOverview)
    AppCompatTextView tvOverview;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    private AccountsViewModel accountsViewModel;

    private AccountsRecyclerAdapter accountsRecyclerAdapter;

    private UIHelper uiHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_accouts);

        ButterKnife.bind(this);

        setupAccountsRecyclerView();

        initObjects();

        subscribeAccountsList();
    }

    private void initObjects() {
        uiHelper = new UIHelper();
        accountsViewModel = ViewModelProviders.of(this).get(AccountsViewModel.class);
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void setupAccountsRecyclerView() {
        accountsRecyclerAdapter = new AccountsRecyclerAdapter(this);
        recyclerAccounts.setLayoutManager(new LinearLayoutManager(this));
        recyclerAccounts.addItemDecoration(new CommonItemDecorator());
        recyclerAccounts.setAdapter(accountsRecyclerAdapter);
        accountsRecyclerAdapter.setOnItemClickListener(new AccountsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(AccountTransactions accountTransactions) {
                Intent intent = new Intent(AccountsActivity.this, AccountTransactionDetails.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Statics.BUNDLE_ACCOUNT_OBJ, accountTransactions);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void subscribeAccountsList() {
        accountsViewModel.getAccountsList().observe(this, new Observer<List<AccountTransactions>>() {
            @Override
            public void onChanged(@Nullable List<AccountTransactions> accounts) {
                accountsRecyclerAdapter.setAccounts(accounts);
            }
        });
    }

    @Override
    public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {
        if (uiHelper.isActivityAlive(this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!(uiHelper.isActivityAlive(AccountsActivity.this)
                            && appBarLayout != null)) {
                        return;
                    }
                    float verticalOffsetPercentage = Math.abs(verticalOffset) * 1.0f /
                            appBarLayout.getTotalScrollRange();
                    float actualVal = 1 - verticalOffsetPercentage;
                    if (actualVal > .9) {
                        tvOverview.setAlpha(1);
                    } else {
                        tvOverview.setAlpha(actualVal / 5);
                    }
                }
            });
        }
    }

}
