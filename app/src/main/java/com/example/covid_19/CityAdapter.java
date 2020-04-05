package com.example.covid_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CityAdapter<J> extends ArrayAdapter {

    private final JSONArray cities;

    public CityAdapter(Context context, int i, JSONArray cities) {
        super(context, i, Collections.singletonList(cities));
        this.cities = cities;
    }

    @Override
    public int getCount() {
        super.getCount();
        System.out.println(cities.length());
        return cities.length();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        try {
            return cities.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        System.out.println(cities);
        JSONObject city = (JSONObject) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_covid_data_item_list, parent, false);
        }
        // Lookup view for data population
        TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
        TextView cityConfirmed = (TextView) convertView.findViewById(R.id.city_confirmed);
        // Populate the data into the template view using the data object
        try {
            cityName.setText(city.getString("name"));
            cityConfirmed.setText(city.getJSONObject("detail").getString("confirmed"));
        } catch (JSONException e) {
            System.out.println(city);
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
