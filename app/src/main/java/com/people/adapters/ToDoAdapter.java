package com.people.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.people.R;
import com.people.root.DashboardActivity;

/**
 * Created by admin on 5/15/2018.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tv_flag;

        public MyViewHolder(View view) {
            super(view);
            tv_flag = (TextView) view.findViewById(R.id.tv_flag);
            tv_flag.setVisibility(View.GONE);
        }
    }


    public ToDoAdapter(Context mContext) {
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