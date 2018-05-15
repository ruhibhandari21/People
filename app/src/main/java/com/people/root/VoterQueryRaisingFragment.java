package com.people.root;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.people.R;

/**
 * Created by admin on 5/15/2018.
 */

public class VoterQueryRaisingFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private Button tv_request, tv_complaint, tv_suggestion, tv_feedback;
    private EditText edt_title, edt_description;
    private Button btn_back, btn_submit, btn_attach_file;

    public VoterQueryRaisingFragment() {
        // Required empty public constructor
    }

    public static VoterQueryRaisingFragment newInstance(String param1, String param2) {
        VoterQueryRaisingFragment fragment = new VoterQueryRaisingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_request:
                ((DashboardActivity)mContext).callSetupFragment(DashboardActivity.SCREENS.REQUEST,null);
                break;
            case R.id.tv_complaint:
                break;
            case R.id.tv_suggestion:
                break;
            case R.id.tv_feedback:
                break;
            case R.id.btn_back:
                ((DashboardActivity)mContext).callSetupFragment(DashboardActivity.SCREENS.PUSHUPDATES,null);
                break;
        }
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
        view = inflater.inflate(R.layout.fragment_voter_query_raising, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }

    public void initUI() {
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_request = (Button) view.findViewById(R.id.tv_request);
        tv_complaint = (Button) view.findViewById(R.id.tv_complaint);
        tv_suggestion = (Button) view.findViewById(R.id.tv_suggestion);
        tv_feedback = (Button) view.findViewById(R.id.tv_feedback);
        btn_back=(Button)view.findViewById(R.id.btn_back);
        tv_request.setTypeface(font);
        tv_complaint.setTypeface(font);
        tv_suggestion.setTypeface(font);
        tv_feedback.setTypeface(font);
    }


    public void initListener() {
        tv_request.setOnClickListener(this);
        tv_complaint.setOnClickListener(this);
        tv_suggestion.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

}
