
package com.example.cecs448;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private ArrayList<Transaction> transactionList;
    private Context context;
    private OnTransactionListener mOnTransactionListener;

    //context is the activity
    public TransactionsAdapter(ArrayList<Transaction> transactions, Context context, OnTransactionListener onTransactionListener) {
        this.transactionList = transactions;
        this.context = context;
        this.mOnTransactionListener = onTransactionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnTransactionListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //for the two place decimal
        DecimalFormat df = new DecimalFormat("0.00");
        Transaction transaction = transactionList.get(position);
        holder.tvCategory.setText(transactionList.get(position).getCategory());
        holder.tvAmount.setText("$" + df.format(transactionList.get(position).getAmount()).toString());
        /***********************/
        holder.tvDate.setText(transactionList.get(position).getDateToString());
    }

    @Override
    public int getItemCount() {
        if(transactionList != null){
            return transactionList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView cvTransaction;
        public TextView tvCategory;
        public TextView tvAmount;
        public TextView tvDate;
        public OnTransactionListener onTransactionListener;

        public ViewHolder(View itemView, OnTransactionListener onTransactionListener) {
            super(itemView);
            cvTransaction = (CardView) itemView.findViewById(R.id.cvTransaction);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);

            this.onTransactionListener = onTransactionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTransactionListener.onTransactionClick(getAdapterPosition());
        }
    }

    public interface OnTransactionListener{
        void onTransactionClick(int position);
    }
}