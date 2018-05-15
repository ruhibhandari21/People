package com.people.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.people.R;
import com.people.root.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/15/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> arrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        private Button btn_follow;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            btn_follow=(Button)view.findViewById(R.id.btn_follow);
        }
    }


    public SearchAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(arrayList.get(position));
        if(position%2==0)
        {
            holder.btn_follow.setText("Follow");
            holder.btn_follow.setBackgroundColor(mContext.getResources().getColor(R.color.colorHeader));
        }
        else
        {
            holder.btn_follow.setText("Unfollow");
            holder.btn_follow.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}