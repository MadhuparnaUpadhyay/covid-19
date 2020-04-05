package com.example.covid_19;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.covid_19.Common.ServerCallback;
import com.example.covid_19.Common.VollyServerCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CovidDataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private JSONArray statewise;
    private JSONObject citywise;
    private SwipeRefreshLayout swipeRefreshLayout;
        private RecyclerView recyclerView;
    private ExpandableListView expListView;
    private int lastExpandedPosition = -1;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CovidDataFragment newInstance(int columnCount) {
        CovidDataFragment fragment = new CovidDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_covid_data_list, container, false);

        statewise = new JSONArray();
        citywise = new JSONObject();
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout.setOnRefreshListener(this);
//        expListView = (ExpandableListView) view.findViewById(R.id.covid_data_expan);

        getData();
        mListener = new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(int position) {
                JSONArray cities = getCity(position);
//                Toast.makeText(CovidDataFragment.this.getContext(), "Position " + "position", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), CityScrollingActivity.class);
//                intent.putExtra("city", cities.toString());
//                startActivity(intent);
            }
        };

        return view;
    }

    private JSONArray getCity(Integer position){
        JSONObject stateObj = new JSONObject();
        JSONArray cities = new JSONArray();
        try {
            stateObj = statewise.getJSONObject(position);
            final JSONObject city = citywise.getJSONObject(stateObj.getString("state")).getJSONObject("districtData");
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        expListView.setIndicatorBounds(expListView.getLeft()- 40, expListView.getWidth());
    }

    private void getData() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_STATE = "http://34.93.188.103/api/v1/covid/data";
        final String MAIN_URL_CITY = "http://34.93.188.103/api/v1/covid/detail-data";
        controller.JsonObjectRequest(getContext(), MAIN_URL_STATE, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        try {
                            statewise = response.getJSONArray("statewise");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        controller.JsonObjectRequest(getContext(), MAIN_URL_CITY, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        citywise = response;
                       recyclerView.setAdapter(new MyCovidDataRecyclerViewAdapter(statewise, citywise, mListener));
                        // setting list adapter
//                        expListView.setAdapter(new CovidDataExpandableListAdapter(getContext(), statewise, citywise, mListener));
//
//                        // Listview Group click listener
//                        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//                            @Override
//                            public boolean onGroupClick(ExpandableListView parent, View v,
//                                                        int groupPosition, long id) {
//                                return false;
//                            }
//                        });
//
//                        // Listview Group expanded listener
//                        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//                            @Override
//                            public void onGroupExpand(int groupPosition) {
//                                if (lastExpandedPosition != -1
//                                        && groupPosition != lastExpandedPosition) {
//                                    expListView.collapseGroup(lastExpandedPosition);
//                                }
//                                lastExpandedPosition = groupPosition;
//                            }
//                        });
//
//                        // Listview Group collasped listener
//                        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//                            @Override
//                            public void onGroupCollapse(int groupPosition) {
//
//                            }
//                        });
                    }
                }
        );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        getData(); // your code
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int view);
    }
}
