package live.combatemic.app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.VollyServerCall;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityBarChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityBarChart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BarChart chart;
    private JSONArray statewise = new JSONArray();
    private String code;
    private TextView noBar;

    public CityBarChart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityBarChart.
     */
    // TODO: Rename and change types and number of parameters
    public static CityBarChart newInstance(String param1, String param2) {
        CityBarChart fragment = new CityBarChart();
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
        View view = inflater.inflate(R.layout.fragment_city_bar_chart, container, false);

        chart = view.findViewById(R.id.barchart);
        noBar = view.findViewById(R.id.no_bar);

//        List<BarEntry> NoOfEmp = new ArrayList<BarEntry>();
//
//        NoOfEmp.add(new BarEntry(945f, 0));

        getData();

        return view;
    }

    private void getData() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_STATE = "/states_daily.json";
        controller.JsonObjectRequest(getContext(), MAIN_URL_STATE, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        try {
                            statewise = response.getJSONArray("states_daily");
                            if (code != null && !code.equals("")) {
                                setBarChart();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private void setBarChart() {
        final int MAX_SIZE_GRAPH = 5;
        boolean isNoBar = true;
        final List<String> list_x_axis_name = new ArrayList<String>(MAX_SIZE_GRAPH);
        final List<BarEntry> barEntries = new ArrayList<BarEntry>(MAX_SIZE_GRAPH);
        for (int i = statewise.length() - 1; i > 0 && barEntries.size() < MAX_SIZE_GRAPH; i--) {
            try {
                String todayCase = statewise.getJSONObject(i).getString(code.toLowerCase());
                String status = statewise.getJSONObject(i).getString("status");
                String[] date = statewise.getJSONObject(i).getString("date").split(("-"));
                if (status.equals("Confirmed")) {
                    if(Integer.parseInt(todayCase) > 0) {
                        isNoBar = false;
                    }
                    barEntries.add(new BarEntry(Float.parseFloat(todayCase), barEntries.size()));
                    list_x_axis_name.add(date[0] + " " + date[1]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        BarDataSet bardataset = new BarDataSet(barEntries, "");

        BarData data = new BarData(list_x_axis_name, bardataset);

//        bardataset.resetColors(Color.rgb(230, 230, 230));
        chart.animateY(3000);

        data.setValueTextSize(15);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int) value + "";
            }
        });

        bardataset.setColors(new int[]{Color.parseColor("#EE9197")});
        XAxis xAxis = chart.getXAxis();
//                            xAxis.setValueFormatter(new IndexAxisValueFormatter(list_x_axis_name));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(13);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setExtraBottomOffset(4);
        chart.setDescription("");
        chart.setData(data);
        if(isNoBar) {
            noBar.setVisibility(View.VISIBLE);
            chart.setVisibility(View.GONE);
        } else {
            noBar.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
        }
    }

    void doSomething(String param) {
        // do something in fragment
        code = param;
        if (statewise.length() > 0)
            setBarChart();
    }

}
