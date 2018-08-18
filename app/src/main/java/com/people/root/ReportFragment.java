package com.people.root;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
//
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.people.ApiCalls;
import com.people.ProgressFragment;
import com.people.R;
import com.people.utils.AppConstants;
import com.people.utils.PreferencesManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 5/15/2018.
 */

public class ReportFragment extends Fragment implements View.OnClickListener, ApiCalls.ApiCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private TextView tv_title;
    private Button tv_prorities, tv_statistics,btn_back;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        view = inflater.inflate(R.layout.fragment_report, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }


    public void initUI() {
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setTypeface(font);
        tv_prorities = (Button) view.findViewById(R.id.tv_prorities);
        tv_statistics = (Button) view.findViewById(R.id.tv_statistics);
        btn_back=(Button)view.findViewById(R.id.btn_back);
        tv_statistics.setTypeface(font);
        tv_prorities.setTypeface(font);
        btn_back.setTypeface(font);
    }

    public void initListener() {
        tv_prorities.setOnClickListener(this);
        tv_statistics.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_prorities:
                ((DashboardActivity)mContext).callSetupFragment(AppConstants.Screens.PRIORITIES,null);
                break;
            case R.id.tv_statistics:
                ((DashboardActivity)mContext).callSetupFragment(AppConstants.Screens.PIECHART,null);
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

    private void apiCalls(String tag){

        showProgress(false);
        Map<String, String> params = new HashMap<String, String>();
        ApiCalls apiCalls = new ApiCalls(this);

        switch (tag){

            case AppConstants.WebApi.GET_REPORT:
                params.put("userId", PreferencesManager.getInstance(getActivity()).
                        getInt(AppConstants.Preference.PREF_USER_ID)+"");
                break;

        }
        apiCalls.initiateRequest(Request.Method.POST, tag,new JSONObject(params), tag);
    }

    private void showProgress(boolean isDismiss){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = new ProgressFragment();
        if(!isDismiss)
            dialogFragment.show(ft, "dialog");
    }

    @Override
    public void onResponse(JSONObject response, String TAG) {

        switch (TAG){
            case AppConstants.WebApi.GET_REPORT:
                break;
        }

    }

    @Override
    public void onError(VolleyError error, String TAG) {
        switch (TAG){
            case AppConstants.WebApi.GET_REPORT:
                break;
        }
    }
}
