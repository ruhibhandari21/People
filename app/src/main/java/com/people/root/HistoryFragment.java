package com.people.root;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.people.R;
import com.people.adapters.HistoryAdapter;

/**
 * Created by admin on 5/15/2018.
 */

public class HistoryFragment  extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private EditText edt_title, edt_description;
    private Button btn_back, btn_publish, btn_attach_file;
    private RecyclerView recycler_view;
    private HistoryAdapter historyAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_push_updates, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }


    public void initUI()
    {
        recycler_view=(RecyclerView)view.findViewById(R.id.recycler_view);
        historyAdapter = new HistoryAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(historyAdapter);
    }

    public void initListener()
    {

    }

    @Override
    public void onClick(View v) {

    }
}
