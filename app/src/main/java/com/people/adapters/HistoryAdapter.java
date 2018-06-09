package com.people.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.R;
import com.people.root.DashboardActivity;

import java.util.List;

/**
 * Created by admin on 5/15/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_flag;

        public MyViewHolder(View view) {
            super(view);
            tv_flag = (TextView) view.findViewById(R.id.tv_flag);
        }
    }


    public HistoryAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(position%2==0)
        {
            holder.tv_flag.setText(mContext.getResources().getText(R.string.request));
            holder.tv_flag.setTextColor(mContext.getResources().getColor(R.color.colorHeader));
        }
        else if(position==3)
        {
            holder.tv_flag.setText(mContext.getResources().getText(R.string.complaint));
            holder.tv_flag.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }
        else
        {
            holder.tv_flag.setText(mContext.getResources().getText(R.string.suggestion));
            holder.tv_flag.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((DashboardActivity)mContext).callSetupFragment(DashboardActivity.SCREENS.VOTERVIEWFEEDBACK,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;//moviesList.size();
    }
}