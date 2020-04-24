package live.combatemic.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.VollyServerCall;

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
public class StateDetailsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private JSONArray statewise;
    private JSONObject citywise;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView lastLocation;
    private ExpandableListView expListView;
    private int lastExpandedPosition = -1;
    private SearchView searchview;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StateDetailsFragment newInstance(int columnCount) {
        StateDetailsFragment fragment = new StateDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_state_details_list, container, false);

        statewise = new JSONArray();
        citywise = new JSONObject();
        lastLocation = view.findViewById(R.id.last_location);
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout.setOnRefreshListener(this);
        searchview = (SearchView) view.findViewById(R.id.search_view);
        searchview.setOnQueryTextListener(this);
//        expListView = (ExpandableListView) view.findViewById(R.id.city_data_expan);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String address = sharedPref.getString("address", "");
        if(address.length() == 0){
            address = "Your location has not been yet updated";
        }
        lastLocation.setText(address);

        getData();
        mListener = new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(int position, String finalStateName) {
                if(!finalStateName.toLowerCase().equals("total")) {
                    JSONArray cities = getCity(finalStateName);
                    Intent intent = new Intent(getContext(), CityScrollingActivity.class);
                    intent.putExtra("city", cities.toString());
                    intent.putExtra("state", finalStateName);
                    startActivity(intent);
                }
            }
        };

        return view;
    }

    private JSONArray getCity(String finalStateName){
        JSONObject stateObj = new JSONObject();
        JSONArray cities = new JSONArray();
        try {
//            stateObj = statewise.getJSONObject(position);
            final JSONObject city = citywise.getJSONObject(finalStateName).getJSONObject("districtData");
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        JSONArray filterState = filterState(newText);
        recyclerView.setAdapter(new StateDetailsRecyclerViewAdapter(filterState, citywise, mListener));
        return false;
    }

    private JSONArray filterState(String text) {
        JSONArray newJson = new JSONArray();
        if (text == null) {
            return statewise;
        }
        try {
            for (int i = 0; i < statewise.length(); i++) {
                JSONObject state = statewise.getJSONObject(i);
                if(state.getString("state").toLowerCase().startsWith(text.toLowerCase())){
                    newJson.put(state);
                }
            }
            return newJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statewise;
    }

    private void getData() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_STATE = "data";
        final String MAIN_URL_CITY = "detail-data";
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
                       recyclerView.setAdapter(new StateDetailsRecyclerViewAdapter(statewise, citywise, mListener));
                        // setting list adapter
//                        expListView.setAdapter(new CityExpandableListAdapter(getContext(), statewise, citywise, mListener));
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
    public void onPause() {
        super.onPause();
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchview != null) {
//            CharSequence text = searchview.getQuery();
//            if(text.length() == 0){
//                Toast.makeText(getContext(), ""+searchview.getQuery(), Toast.LENGTH_SHORT).show();
//                getData();
//            }
            searchview.clearFocus();
        }
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
        void onListFragmentInteraction(int view, String finalStateName);
    }
}
