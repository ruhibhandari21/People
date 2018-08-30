package com.people.root;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.people.R;
import com.people.utils.AppConstants;

/**
 * Created by admin on 5/15/2018.
 */

public class VoterViewFeedbackScreen extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private TextView tv_feedback, tv_title;
    private Button btn_back;

    public VoterViewFeedbackScreen() {
        // Required empty public constructor
    }

    public static VoterViewFeedbackScreen newInstance(String param1, String param2) {
        VoterViewFeedbackScreen fragment = new VoterViewFeedbackScreen();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                ((DashboardActivity) mContext).callSetupFragment(AppConstants.Screens.HISTORY, null);
                break;
        }
    }

    public void initUI() {
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_feedback = (TextView) view.findViewById(R.id.tv_feedback);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_feedback.setTypeface(font);
        tv_title.setTypeface(font);
        btn_back.setTypeface(font);
    }

    public void initListener() {
        btn_back.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_voter_view_feedback, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }


}
