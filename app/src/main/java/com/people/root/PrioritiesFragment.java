package com.people.root;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.people.R;
import com.people.utils.AppConstants;

import java.util.ArrayList;

/**
 * Created by admin on 5/15/2018.
 */

public class PrioritiesFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View view;
    private PieChart pieChart;
    private Button btn_back,btn_share;

    public PrioritiesFragment() {
        // Required empty public constructor
    }

    public static PrioritiesFragment newInstance(String param1, String param2) {
        PrioritiesFragment fragment = new PrioritiesFragment();
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
        view = inflater.inflate(R.layout.fragment_priorities, container, false);
        mContext = getActivity();
        initUI();
        initListener();
        return view;
    }


    public void initUI() {
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_share= (Button) view.findViewById(R.id.btn_share);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Caviar_Dreams_Bold.ttf");
        btn_back.setTypeface(font);
        btn_share.setTypeface(font);
        ((DashboardActivity) mContext).floatingActionButton.setVisibility(View.GONE);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(8f, 0));
        yvalues.add(new Entry(15f, 1));
        yvalues.add(new Entry(12f, 2));


        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("High");
        xVals.add("Medium");
        xVals.add("Low");

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(13f);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
    }

    public void initListener() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                ((DashboardActivity)mContext).callSetupFragment(AppConstants.Screens.REPORT,null);
                break;
            case R.id.btn_share:
                Toast.makeText(mContext, "Under development", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
