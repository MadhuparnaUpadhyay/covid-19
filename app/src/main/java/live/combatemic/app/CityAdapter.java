package live.combatemic.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Random;

public class CityAdapter<J> extends ArrayAdapter {

    private final JSONArray cities;

    CityAdapter(Context context, int i, JSONArray cities) {
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
//        if (position == 0)
//            return LayoutInflater.from(getContext()).inflate(R.layout.fragment_state_details_item, null);
//        else {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_state_item_list, parent, false);
//        }

        JSONObject city = (JSONObject) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_state_item_list, parent, false);
        }
        // Lookup view for data population
        TextView firstChar = convertView.findViewById(R.id.first_char);
        TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
        TextView cityConfirmed = (TextView) convertView.findViewById(R.id.city_confirmed);
        TextView cityRecovered = (TextView) convertView.findViewById(R.id.city_recovered);
        TextView cityActive = (TextView) convertView.findViewById(R.id.city_active);
        TextView cityDeath = (TextView) convertView.findViewById(R.id.city_death);
        // Populate the data into the template view using the data object
        try {
            String cityNameValue = city.getString("name") + "";
            GradientDrawable gradientDrawable = (GradientDrawable) firstChar.getBackground().mutate();
            gradientDrawable.setColor(getRandomColor());
            firstChar.setText(cityNameValue.charAt(0) + "");
            cityName.setText(cityNameValue);
            cityConfirmed.setText(city.getJSONObject("detail").getString("confirmed"));
            cityRecovered.setText(city.getJSONObject("detail").getString("recovered"));
            cityActive.setText(city.getJSONObject("detail").getString("active"));
            cityDeath.setText(city.getJSONObject("detail").getString("deceased"));
        } catch (JSONException e) {
            System.out.println(city);
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(256 / 2, rnd.nextInt(256) /2, rnd.nextInt(256)/2, rnd.nextInt(256)/2);
    }
}
