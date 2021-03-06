package com.people.root;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.people.ApiCalls;
import com.people.ProgressFragment;
import com.people.R;
import com.people.adapters.SearchAdapter;
import com.people.utils.AppConstants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 5/15/2018.
 */

public class VoterSearchFragment extends Fragment implements View.OnClickListener,
        ApiCalls.ApiCallback{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private RecyclerView recycler_view;
    private SearchAdapter searchAdapter;
    private Button btn_search;
    private TextView tv_noresult, tv_title;
    private ArrayList<String> namearray = new ArrayList<>();
    private ArrayList<String> sortednamearray = new ArrayList<>();
    private EditText edt_name;

    public VoterSearchFragment() {
        // Required empty public constructor
    }

    public static VoterSearchFragment newInstance(String param1, String param2) {
        VoterSearchFragment fragment = new VoterSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                sortednamearray.clear();
                for (int i = 0; i < namearray.size(); i++) {
                    if (namearray.get(i).toLowerCase().contains(edt_name.getText().toString().trim().toLowerCase())) {
                        sortednamearray.add(namearray.get(i));
                    }
                }
                if (sortednamearray.size() == 0) {
                    tv_noresult.setVisibility(View.VISIBLE);
                    recycler_view.setVisibility(View.GONE);
                } else {
                    tv_noresult.setVisibility(View.GONE);
                    recycler_view.setVisibility(View.VISIBLE);
                    searchAdapter = new SearchAdapter(mContext, sortednamearray);
                    recycler_view.setAdapter(searchAdapter);
                }

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
        view = inflater.inflate(R.layout.fragment_voter_search, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }

    public void prepareList() {
        namearray.add("Ruhi");
        namearray.add("Shadaf");
        namearray.add("Shadaf Fajal");
        namearray.add("Shadaf Fajal Shaikh");
        namearray.add("SFS Shaikh");
        namearray.add("Ruhi Bhandari");
        namearray.add("RuhiInfi");
        namearray.add("Dummy");
        namearray.add("User");
        namearray.add("Balaji");
        namearray.add("Er Balaji");
    }

    public void initUI() {
        prepareList();
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Caviar_Dreams_Bold.ttf");
        tv_noresult = (TextView) view.findViewById(R.id.tv_noresult);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setTypeface(font);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        btn_search = (Button) view.findViewById(R.id.btn_search);
        edt_name = (EditText) view.findViewById(R.id.edt_name);
    }

    public void initListener() {
        btn_search.setOnClickListener(this);
    }

    private void apiCalls(String tag){

        showProgress(false);
        Map<String, String> params = new HashMap<String, String>();
        ApiCalls apiCalls = new ApiCalls(this);

        switch (tag){

            case AppConstants.WebApi.FOLLOW_LEADER:
                params.put("userId", "");
                params.put("leaderId", "");
                params.put("status", "");
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
            case AppConstants.WebApi.FOLLOW_LEADER:
                break;
        }

    }

    @Override
    public void onError(VolleyError error, String TAG) {
        switch (TAG){
            case AppConstants.WebApi.GENERATE_OTP:
                break;
            case AppConstants.WebApi.LOGIN:
                break;
        }
    }

}