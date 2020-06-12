package live.combatemic.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.Utils;
import live.combatemic.app.Common.VollyServerCall;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZoneListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoneListFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private JSONArray jsonArray;
    private ImageButton searchButton;
    private RelativeLayout linearLayout;
    private ConstraintLayout constraintLayout;
    private SearchView searchview;
    private TextView textViewCount;
    private CardView cardView;
    private OnListFragmentInteractionListener mListener;

    public ZoneListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZoneListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoneListFragment newInstance(Integer param1, String param2) {
        ZoneListFragment fragment = new ZoneListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone_list2, container, false);


//        getData();


        recyclerView = (RecyclerView) view.findViewById(R.id.zone_list);

        searchview = (SearchView) view.findViewById(R.id.search_view);
        searchview.setQueryHint("Search district for zone...");
        searchview.setOnQueryTextListener(this);

        textViewCount = view.findViewById(R.id.total_count);

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        linearLayout = view.findViewById(R.id.location_search);
        constraintLayout = view.findViewById(R.id.search_bar_layout);

        cardView = view.findViewById(R.id.count);

        // Set the adapter
//        ZoneFragment zoneFragment = ZoneFragment.newInstance(2);
//        jsonArray = zoneFragment.getZones(mParam2);
//        int count = zoneFragment.getZonesCount();
//
//        textViewCount.setText(jsonArray.length() + "");
//        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, mListener));


        int searchCloseButtonId = searchview.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchview.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage this event.
                constraintLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                searchview.setQuery("", false);
            }
        });

        mListener = new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(JSONObject jsonObject) {
                try {
//                    String statecode = jsonObject.getString("statecode");
                    String stateName = jsonObject.getString("state");
                    Intent intent = new Intent(getContext(), CityScrollingActivity.class);
                    intent.putExtra("state", stateName);
//                    intent.putExtra("code", statecode);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        return view;
    }

    void displayReceivedData(JSONArray jsonArray) {
        textViewCount.setText(jsonArray.length() + "");
        if (searchview != null) {
            CharSequence text = searchview.getQuery();
            if (text.length() == 0) {
                recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, mListener));
            }
        }
    }

    void displayReceivedCount(int count) {
//        textViewCount.setText(count + "");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        JSONArray jsonArray = filterZone(newText);
        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, mListener));
        return false;
    }

    private JSONArray filterZone(String text) {
        JSONArray newJson = new JSONArray();
        if (text == null) {
            return jsonArray;
        }
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject city = jsonArray.getJSONObject(i);
                if (city.getString("district").toLowerCase().startsWith(text.toLowerCase())) {
                    newJson.put(city);
                }
            }
            return newJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.CloseKeyboard(getContext(), getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
//        ZoneFragment zoneFragment = ZoneFragment.newInstance(2);
//        JSONArray jsonArray = zoneFragment.getZones(mParam2);
//
//        int count = zoneFragment.getZonesCount();
//
//        textViewCount.setText(jsonArray.length() + "");
//
//        if (mParam2.equals("Red")) {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_zone));
//        } else if (mParam2.equals("Green")) {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_zone));
//        } else {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange_zone));
//        }
//
        if (searchview != null) {
            CharSequence text = searchview.getQuery();
            if (text.length() == 0) {
//                recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, mListener));

                constraintLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                getData();
            }
            searchview.clearFocus();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button) {
            constraintLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void getData() {
        jsonArray = new JSONArray();
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_CITY = "/state_district_wise.json";
        controller.JsonObjectRequest(getContext(), MAIN_URL_CITY, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Iterator<String> keys = response.keys();

                        while(keys.hasNext()) {
                            String key = keys.next();
                            try {
                                if (response.get(key) instanceof JSONObject) {
                                    // do something with jsonObject here
                                    JSONArray zoneArray = sortCities(response.getJSONObject(key), key);
                                    recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, mListener));
                                    textViewCount.setText(jsonArray.length() + "");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
    }

    private JSONArray sortCities(JSONObject jsonObject, String state) {
        JSONArray sortedJsonArray = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();

        Iterator<String> keys = null;
        JSONObject districtData = null;
        try {
            districtData = jsonObject.getJSONObject("districtData");
            keys = districtData.keys();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert keys != null;
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                if (districtData.get(key) instanceof JSONObject) {
                    // do something with jsonObject here
                    JSONObject jsonObject1 = districtData.getJSONObject(key);
                    jsonObject1.put("district", key + "");
                    jsonObject1.put("state", state);
                    jsonValues.add(jsonObject1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "active";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                int valA = -1;
                int valB = -1;

                try {
                    valA = Integer.parseInt(a.getString(KEY_NAME));
                    valB = Integer.parseInt(b.getString(KEY_NAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return valB - valA;
            }
        });

        for (int i = 0; i < 3 && i < jsonValues.size(); i++) {
            try {
                if(jsonValues.get(i).getInt("active") > 40) {
                    jsonArray.put(jsonValues.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sortedJsonArray;
    }


    void setupDispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Utils.inViewInBounds(constraintLayout, (int) event.getRawX(), (int) event.getRawY())) {
                // User moved outside bounds
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else if (Utils.inViewInBounds(searchButton, (int) event.getRawX(), (int) event.getRawY())) {
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else {
                if (searchview.getQuery().length() == 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.GONE);
                }
                Log.e("dispatchTouchEvent", "you touched outside button");
            }

        }
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(JSONObject item);
    }

}
