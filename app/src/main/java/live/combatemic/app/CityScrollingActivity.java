package live.combatemic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import live.combatemic.app.Common.Utils;
import live.combatemic.app.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



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

public class CityScrollingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private JSONArray cities;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.city_list);
        SearchView searchview = (SearchView) findViewById(R.id.search_view);
        searchview.setOnQueryTextListener(this);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("city");
        String stateName = intent.getStringExtra("state");

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

        try {
            cities = new JSONArray(jsonArray);
            final CityAdapter<JSONArray> adapter = new CityAdapter<JSONArray>(this, 0, cities);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        JSONArray filterCity = filterCity(newText);
        final CityAdapter<JSONArray> adapter = new CityAdapter<JSONArray>(this, 0, filterCity);
        listView.setAdapter(adapter);
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
                if(city.getString("name").toLowerCase().startsWith(text.toLowerCase())){
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
}
