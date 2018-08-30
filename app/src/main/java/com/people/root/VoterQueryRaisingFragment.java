package com.people.root;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.people.R;
import com.people.utils.AppConstants;

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

        int screens = AppConstants.Screens.REQUEST;
        if(!mParam1.equals("")){
            screens = AppConstants.Screens.TODO;
        }
        switch (v.getId()) {
            case R.id.tv_request:
                ((DashboardActivity)mContext).callSetupFragment(screens,"Request");
                break;
            case R.id.tv_complaint:
                ((DashboardActivity)mContext).callSetupFragment(screens,"Complaint");
                break;
            case R.id.tv_suggestion:
                ((DashboardActivity)mContext).callSetupFragment(screens,"Suggestion");
                break;
            case R.id.tv_feedback:
                ((DashboardActivity)mContext).callSetupFragment(screens,"Feedback");
                break;
            case R.id.btn_back:
                FragmentManager fragmentManager = ((DashboardActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PostFragment fragment = PostFragment.newInstance("", "");
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.inner_frame, fragment, AppConstants.Screens.HOME+"");
                fragmentTransaction.commitAllowingStateLoss();
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
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
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
        btn_back.setTypeface(font);

        if(!mParam1.equals("")){
            tv_request.setText(tv_request.getText().toString()+"(3)");
            tv_complaint.setText(tv_complaint.getText().toString()+"(2)");
            tv_suggestion.setText(tv_suggestion.getText().toString()+"(0)");
            tv_feedback.setText(tv_feedback.getText().toString()+"(5)");
        }
    }


    public void initListener() {
        tv_request.setOnClickListener(this);
        tv_complaint.setOnClickListener(this);
        tv_suggestion.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

}
