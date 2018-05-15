package com.people.root;

/**
 * Created by admin on 5/15/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.people.R;
import com.people.utils.AppConstants;

import static android.app.Activity.RESULT_OK;


public class RequestFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private EditText edt_title, edt_description;
    private Button btn_back, btn_submit, btn_attach_file;
    private TextView tv_title;

    public RequestFragment() {
        // Required empty public constructor
    }

    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
        view = inflater.inflate(R.layout.fragment_request, container, false);
        mContext = getActivity();

        initUI();
        initListener();
        return view;
    }

    public void initUI() {
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
        edt_title = (EditText) view.findViewById(R.id.edt_title);
        edt_description = (EditText) view.findViewById(R.id.edt_description);
        btn_attach_file = (Button) view.findViewById(R.id.btn_attach_file);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        tv_title=(TextView)view.findViewById(R.id.tv_title);
        tv_title.setText(mParam1);
    }

    public void initListener() {
        btn_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_attach_file.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                ((DashboardActivity)mContext).callSetupFragment(DashboardActivity.SCREENS.VOTERQUERYRAISING,null);
                break;
            case R.id.btn_submit:
                Toast.makeText(mContext, "Under Implementation", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_attach_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, AppConstants.PICKFILE_RESULT_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case AppConstants.PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    Toast.makeText(mContext, FilePath, Toast.LENGTH_SHORT).show();
                    //textFile.setText(FilePath);
                }
                break;

        }
    }
}
