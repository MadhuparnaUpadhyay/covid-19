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
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import live.combatemic.app.Common.Utils;


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
    private LinearLayout linearLayout;
    private ConstraintLayout constraintLayout;
    private SearchView searchview;
    private TextView textViewCount;
    private CardView cardView;

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
        ZoneFragment zoneFragment = ZoneFragment.newInstance(2);
        jsonArray = zoneFragment.getZones(mParam2);
        int count = zoneFragment.getZonesCount();

        textViewCount.setText(jsonArray.length() + "");
        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, null));


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


        return view;
    }

    void displayReceivedData(JSONArray jsonArray) {
        textViewCount.setText(jsonArray.length() + "");
        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, null));
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
        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, null));
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
        ZoneFragment zoneFragment = ZoneFragment.newInstance(2);
        JSONArray jsonArray = zoneFragment.getZones(mParam2);

        int count = zoneFragment.getZonesCount();

        textViewCount.setText(jsonArray.length() + "");

        if (mParam2.equals("Red")) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_zone));
        } else if (mParam2.equals("Green")) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_zone));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange_zone));
        }

        recyclerView.setAdapter(new ZoneRecyclerViewAdapter(jsonArray, null));
        if (searchview != null) {
            CharSequence text = searchview.getQuery();
//            if (text.length() == 0) {
            constraintLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
//            }
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

    void setupDispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Utils.inViewInBounds(constraintLayout, (int) event.getRawX(), (int) event.getRawY())) {
                // User moved outside bounds
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else if (Utils.inViewInBounds(searchButton, (int) event.getRawX(), (int) event.getRawY())) {
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);
                Log.e("dispatchTouchEvent", "you touched outside button");
            }

        }
    }

}
