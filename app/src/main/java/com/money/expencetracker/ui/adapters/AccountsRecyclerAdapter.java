package com.money.expencetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.money.expencetracker.R;
import com.money.expencetracker.data.entities.AccountTransactions;
import com.money.expencetracker.helpers.AccountHelper;
import com.money.expencetracker.utilities.CommonUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by abhishek on 13/01/18.
 */

public class AccountsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AccountTransactions> accounts;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public AccountsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setAccounts(List<AccountTransactions> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item_view, null, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountViewHolder accountViewHolder = (AccountViewHolder) holder;
        AccountTransactions account = accounts.get(position);
        accountViewHolder.tvTotalBal.setText(account.getAccountBalance());
        String noOfTransactions = account.getTransactionCount() + "  " + context.getString(R.string.transactions);
        accountViewHolder.tvNoOfTransactions.setText(noOfTransactions);
        String lastTransactionTime = context.getString(R.string.transaction_time) + " " + CommonUtilities.convertDate(account.getTimestamp().longValue(), "dd-MMM-yyyy");
        accountViewHolder.tvTransactionTime.setText(lastTransactionTime);
        int bankImage = AccountHelper.getBankImage(account.getAccountName());
        if (bankImage != -1) {
            accountViewHolder.imgAccount.setImageResource(bankImage);
        } else {
            accountViewHolder.imgAccount.setImageBitmap(null);
        }
    }

    @Override
    public int getItemCount() {
        return accounts != null ? accounts.size() : 0;
    }

    protected class AccountViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTotalBal)
        AppCompatTextView tvTotalBal;
        @BindView(R.id.tvNoOfTransactions)
        AppCompatTextView tvNoOfTransactions;
        @BindView(R.id.tvTransactionTime)
        AppCompatTextView tvTransactionTime;
        @BindView(R.id.imgAccount)
        AppCompatImageView imgAccount;

        public AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.accountItemView)
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            if (onItemClickListener != null) {
                onItemClickListener.onClick(accounts.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        public void onClick(AccountTransactions accountTransactions);
    }

}
