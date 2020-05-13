package live.combatemic.app;

import android.content.Intent;
import android.os.Bundle;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.Utils;
import live.combatemic.app.Common.VollyServerCall;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CityScrollingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupClickListener {

    private JSONArray cities;
    private ListView listView;
    private TextView textView1, textView2, textView3, textView4, textView5;
    private TextView textViewTo1, textViewTo2, textViewTo3, textViewTo4, textViewTo5;
    private TextView textViewSt1, textViewSt2, textViewSt3, textViewSt4, textViewSt5;
    private ImageButton searchButton;
    private ConstraintLayout constraintLayout;
    private ExpandableListView expListView;
    private int lastExpandedPosition = -1;
    private String stateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_scrolling);


        textView1 = findViewById(R.id.state_confirmed);
        textView2 = findViewById(R.id.state_confirmed1);
        textView3 = findViewById(R.id.state_confirmed2);
        textView4 = findViewById(R.id.state_confirmed4);
        textView5 = findViewById(R.id.state_confirmed5);

        textViewTo1 = findViewById(R.id.state_confirmed_today);
        textViewTo2 = findViewById(R.id.state_confirmed_today1);
        textViewTo3 = findViewById(R.id.state_confirmed_today2);
        textViewTo4 = findViewById(R.id.state_confirmed_today4);
        textViewTo5 = findViewById(R.id.state_confirmed_today5);

        textViewSt1 = findViewById(R.id.state_name);
        textViewSt2 = findViewById(R.id.state_name1);
        textViewSt3 = findViewById(R.id.state_name2);
        textViewSt4 = findViewById(R.id.state_name4);
        textViewSt5 = findViewById(R.id.state_name5);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        constraintLayout = findViewById(R.id.search_bar_layout);

        expListView = (ExpandableListView) findViewById(R.id.city_data_expan);
        expListView.setGroupIndicator(null);
        expListView.setOnGroupExpandListener(this);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("city");
        stateName = intent.getStringExtra("state");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.city_list);
        SearchView searchview = (SearchView) findViewById(R.id.search_view);
        searchview.setQueryHint("Search district...");
        searchview.setOnQueryTextListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(stateName);
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setCustomView(R.layout.custom_toolbar);
            View view = getSupportActionBar().getCustomView();

            ImageView imageView = (ImageView) view.findViewById(R.id.back_button);
            TextView textView = view.findViewById(R.id.toolbar_title);
            textView.setText(stateName);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        getData();

//            ViewTreeObserver vto = expListView.getViewTreeObserver();
//
//            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    Drawable drawable_groupIndicator =
//                            getResources().getDrawable(R.drawable.expandable);
//                    int drawable_width = drawable_groupIndicator.getMinimumWidth();
//
//                    if (android.os.Build.VERSION.SDK_INT <
//                            android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                        expListView.setIndicatorBounds(
//                                expListView.getWidth() - drawable_width - 20,
//                                expListView.getWidth() - 20);
//                    } else {
//                        expListView.setIndicatorBoundsRelative(
//                                expListView.getWidth() - drawable_width - 20,
//                                expListView.getWidth() -  20);
//                    }
//                }
//            });

    }

    JSONArray sortCities(JSONArray jsonArray) {
        JSONArray sortedJsonArray = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonValues.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "confirmed";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                int valA = -1;
                int valB = -1;

                try {
                    valA = Integer.parseInt(a.getJSONObject("detail").getString(KEY_NAME));
                    valB = Integer.parseInt(b.getJSONObject("detail").getString(KEY_NAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(valA + " , " + valB);
                return valB - valA;
            }
        });

        for (int i = 0; i < jsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        JSONArray filterCity = filterCity(newText);
        JSONArray jsonArray1 = sortCities(filterCity);
        CityExpandableListAdapter cityExpandableListAdapter = new CityExpandableListAdapter(this, jsonArray1);
        expListView.setAdapter(cityExpandableListAdapter);
//        final CityAdapter<JSONArray> adapter = new CityAdapter<JSONArray>(this, 0, jsonArray1);
//        listView.setAdapter(adapter);
        return false;
    }

    private JSONArray filterCity(String text) {
        JSONArray newJson = new JSONArray();
        if (text == null) {
            return cities;
        }
        try {
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                if (city.getString("name").toLowerCase().startsWith(text.toLowerCase())) {
                    newJson.put(city);
                }
            }
            return newJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.CloseKeyboard(this, CityScrollingActivity.this);
    }

    void setTopDistrict(JSONArray jsonArray) {
        try {
            for (int i = 0; i < 5; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String state = jsonObject.getString("name");
                JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                String string = jsonObject1.getString("confirmed");
                String string1 = jsonObject1.getJSONObject("delta").getString("confirmed");
                if (i == 0) {
                    textView1.setText(string);
                    textViewTo1.setText(string1);
                    textViewSt1.setText(state);
                } else if (i == 1) {
                    textView2.setText(string);
                    textViewTo2.setText(string1);
                    textViewSt2.setText(state);
                } else if (i == 2) {
                    textView3.setText(string);
                    textViewTo3.setText(string1);
                    textViewSt3.setText(state);
                } else if (i == 3) {
                    textView4.setText(string);
                    textViewTo4.setText(string1);
                    textViewSt4.setText(state);
                } else if (i == 4) {
                    textView5.setText(string);
                    textViewTo5.setText(string1);
                    textViewSt5.setText(state);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button) {
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastExpandedPosition != -1
                && groupPosition != lastExpandedPosition) {
            expListView.collapseGroup(lastExpandedPosition);
        }
        lastExpandedPosition = groupPosition;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    void setAdapter() {
        //            cities = new JSONArray(jsonArray);
        JSONArray jsonArray1 = sortCities(cities);
        CityExpandableListAdapter cityExpandableListAdapter = new CityExpandableListAdapter(this, jsonArray1);
        expListView.setAdapter(cityExpandableListAdapter);
//            final CityAdapter<JSONArray> adapter = new CityAdapter<JSONArray>(this, 0, jsonArray1);
//            listView.setAdapter(adapter);
        setTopDistrict(jsonArray1);
    }

    private void getData() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_CITY = "detail-data";
        controller.JsonObjectRequest(this, MAIN_URL_CITY, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        getCity(response);
                        setAdapter();
                    }
                }
        );
    }

    private void getCity(JSONObject cityDetail) {
        cities = new JSONArray();
        try {
            final JSONObject city = cityDetail.getJSONObject(stateName).getJSONObject("districtData");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Utils.inViewInBounds(constraintLayout, (int) event.getRawX(), (int) event.getRawY())) {
                // User moved outside bounds
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else if (Utils.inViewInBounds(searchButton, (int) event.getRawX(), (int) event.getRawY())) {
                Log.e("dispatchTouchEvent", "you touched inside button");
            } else {
                constraintLayout.setVisibility(View.GONE);
                Log.e("dispatchTouchEvent", "you touched outside button");
            }

        }
        return super.dispatchTouchEvent(event);
    }

}
