package com.money.expencetracker.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.money.expencetracker.R;
import com.money.expencetracker.data.entities.Transaction;
import com.money.expencetracker.utilities.CommonUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abhishek on 13/01/18.
 */

public class TransactionsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    public TransactionsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setTransactions(List<Transaction> accounts) {
        this.transactions = accounts;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_view, parent, false);
        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        TransactionsViewHolder transactionsViewHolder = (TransactionsViewHolder) holder;
        transactionsViewHolder.tvTransactionAmount.setText(transaction.getAmount());
        transactionsViewHolder.tvTransactionTime.setText(CommonUtilities.convertDate(transaction.getTimeStamp().longValue(), "dd-MMM-yyyy"));
        int imgTransactionType = getTransactionTypeImg(transaction.getTransactionType());
        if (imgTransactionType != -1) {
            transactionsViewHolder.imgTransactionType.setImageResource(imgTransactionType);
        } else {
            transactionsViewHolder.imgTransactionType.setImageBitmap(null);
        }
    }

    private int getTransactionTypeImg(String transactionType) {
        switch (transactionType.toLowerCase()) {
            case "incoming":
                return R.drawable.ic_deposit;

            case "outgoing":
                return R.drawable.ic_spent_money;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    protected class TransactionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTransactionAmount)
        AppCompatTextView tvTransactionAmount;

        @BindView(R.id.tvTransactionTime)
        AppCompatTextView tvTransactionTime;

        @BindView(R.id.imgTransactionType)
        AppCompatImageView imgTransactionType;


        public TransactionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
