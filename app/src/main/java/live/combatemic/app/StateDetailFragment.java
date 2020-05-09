package live.combatemic.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView state, confirmed, recovered, death, active, dateTime, lineText, todayConfirmed, todayRecovered, todayDeath;
    private LinearLayout linearLayout;
    private String stateName = null;
    private String statecode = null;
    private JSONObject cityDetail = null;

    public StateDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StateDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StateDetailFragment newInstance(String param1, String param2) {
        StateDetailFragment fragment = new StateDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_state_detail, container, false);

        state = (TextView) view
                .findViewById(R.id.state);
        confirmed = (TextView) view
                .findViewById(R.id.confirmed);
        todayConfirmed = (TextView) view
                .findViewById(R.id.today_confirmed);
        recovered = (TextView) view
                .findViewById(R.id.recovered);
        todayRecovered = (TextView) view
                .findViewById(R.id.today_recovered);
        death = (TextView) view
                .findViewById(R.id.death);
        todayDeath = (TextView) view
                .findViewById(R.id.today_death);
        active = (TextView) view
                .findViewById(R.id.active);
        dateTime = (TextView) view
                .findViewById(R.id.date_time);
        lineText = (TextView) view
                .findViewById(R.id.line);
        linearLayout = (LinearLayout) view
                .findViewById(R.id.details);

        linearLayout.setOnClickListener(this);

        return view;
    }

    void setData(JSONObject stateDetail, JSONObject cityDetail) {
        this.cityDetail = cityDetail;
        CityBarChart fragmentDemo = (CityBarChart)
                getChildFragmentManager().findFragmentById(R.id.barchart);
        try {
            stateName = stateDetail.getString("state");
            statecode = stateDetail.getString("statecode");
            fragmentDemo.doSomething(statecode);
            state.setText(stateName);
            confirmed.setText(stateDetail.getString("confirmed"));
            if (!stateDetail.getString("deltaconfirmed").equals("0")) {
                todayConfirmed.setText(stateDetail.getString("deltaconfirmed"));
            } else {
                todayConfirmed.setVisibility(View.INVISIBLE);
            }
            recovered.setText(stateDetail.getString("recovered"));
            if (!stateDetail.getString("deltarecovered").equals("0")) {
                todayRecovered.setText(stateDetail.getString("deltarecovered"));
            } else {
                todayRecovered.setVisibility(View.INVISIBLE);
            }
            death.setText(stateDetail.getString("deaths"));
            if (!stateDetail.getString("deltadeaths").equals("0")) {
                todayDeath.setText(stateDetail.getString("deltadeaths"));
            } else {
                todayDeath.setVisibility(View.INVISIBLE);
            }
            active.setText(stateDetail.getString("active"));
            dateTime.setText(dateTime(stateDetail.getString("lastupdatedtime")));
            if (stateName.toLowerCase().equals("total")) {
                fragmentDemo.getView().setVisibility(View.GONE);
                lineText.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
            } else {
                fragmentDemo.getView().setVisibility(View.VISIBLE);
                lineText.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String dateTime(String dateString) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            String newDate = "Updated on: " + new SimpleDateFormat("dd MMM").format(date);
            String time = "Time: " + new SimpleDateFormat("hh:mm a").format(date);
            return newDate + ", " + time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        JSONArray cities = getCity(stateName);
        Intent intent = new Intent(getContext(), CityScrollingActivity.class);
        intent.putExtra("city", cities.toString());
        intent.putExtra("state", stateName);
        intent.putExtra("code", statecode);
        startActivity(intent);
    }

    private JSONArray getCity(String finalStateName) {
        JSONObject stateObj = new JSONObject();
        JSONArray cities = new JSONArray();
        try {
            final JSONObject city = cityDetail.getJSONObject(finalStateName).getJSONObject("districtData");
            Iterator<String> keys = city.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (city.get(key) instanceof JSONObject) {
                    JSONObject person = new JSONObject();
                    person.put("name", key);
                    person.putOpt("detail", city.getJSONObject(key));
                    // do something with jsonObject here
                    cities.put(person);
                }
            }
            return cities;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
