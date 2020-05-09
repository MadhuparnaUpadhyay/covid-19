package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.Utils;
import live.combatemic.app.Common.VollyServerCall;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ZoneFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private JSONArray redZones = new JSONArray();
    private JSONArray greenZones = new JSONArray();
    private JSONArray orangeZones = new JSONArray();
    private JSONArray userArray = new JSONArray();
    private ZonePageAdapter mSectionsPagerAdapter;
    private static ZoneFragment fragment;
    private TextView textViewCount;
    private CardView cardView;

    public static ZoneFragment newInstance(Integer columnCount) {
        if (fragment == null) {
            fragment = new ZoneFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zone_list, container, false);

        Context context = view.getContext();
        getData();

//        textViewCount = view.findViewById(R.id.total_count);
//        cardView = view.findViewById(R.id.count);

        // Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new ZonePageAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.zone_pager_container);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#F9617A"));
        // this is done "so that the tab position is kept in sync"
        // what it does is when you swipe the fragment in view pager, it updates the tabs
        mViewPager.addOnPageChangeListener(this);
        // without this listener the tabs would still get updated when fragments are swiped, but ....  (read the next comment)
        tabLayout.addOnTabSelectedListener(this);

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getData() {
        final String MAIN_URL = "https://api.covid19india.org";
        final String MAIN_URL_ZONE = "/zones.json";

        VollyServerCall controller = new VollyServerCall(MAIN_URL);
        controller.JsonObjectRequest(getContext(), MAIN_URL_ZONE, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        try {
                            userArray = response.getJSONArray("zones");

//                            textViewCount.setText(userArray.length() + "");

                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject jsonObject = userArray.getJSONObject(i);
                                String Zone = jsonObject.getString("zone");

                                if (Zone.equals("Red")) {
                                    redZones.put(jsonObject);
                                } else if (Zone.equals("Orange")) {
                                    orangeZones.put(jsonObject);
                                } else {
                                    greenZones.put(jsonObject);
                                }
                            }
                            ZoneListFragment f = (ZoneListFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                            String zone = f.getArguments().getString("param2");
                            JSONArray jsonArray = getZones(zone);
                            f.displayReceivedData(jsonArray);
                            f.displayReceivedCount(userArray.length());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.red_zone));
            ZoneListFragment.newInstance(position, "Red");
        } else if (position == 1) {
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.green_zone));
            ZoneListFragment.newInstance(position, "Green");
        } else {
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.orange_zone));
            ZoneListFragment.newInstance(position, "Orange");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
        if (position == 0) {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_zone));
            tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#F9617A"));
            ZoneListFragment.newInstance(position, "Red");
        } else if (position == 1) {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_zone));
            tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#70BF82"));
            ZoneListFragment.newInstance(position, "Green");
        } else {
//            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange_zone));
            tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#F8A867"));
            ZoneListFragment.newInstance(position, "Orange");
        }
    }

    JSONArray getZones(String Zone) {
        if (Zone.equals("Red")) {
            return redZones;
        } else if (Zone.equals("Orange")) {
            return orangeZones;
        } else if (Zone.equals("Green")) {
            return greenZones;
        }
        return greenZones;
    }

    int getZonesCount() {
        return userArray.length();
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
//        void onListFragmentInteraction(DummyItem item);
    }
}
