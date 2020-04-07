package live.combatemic;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import live.combatemic.R;
import com.google.common.collect.Iterators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class CovidDataExpandableListAdapter extends BaseExpandableListAdapter {

    private final JSONArray statewise;
    private final JSONObject citywise;
    private final CovidDataFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public CovidDataExpandableListAdapter(Context mContext, JSONArray statewise, JSONObject citywise, CovidDataFragment.OnListFragmentInteractionListener mListener) {
        this.mContext = mContext;
        this.statewise = statewise;
        this.citywise = citywise;
        this.mListener = mListener;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
//        if (childPosititon == 0)
//            return null;
        JSONObject stateObj = new JSONObject();
        JSONArray cities = new JSONArray();
        try {
            stateObj = statewise.getJSONObject(groupPosition);
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
    public long getChildId(int groupPosition, int childPosition) {
//        if (childPosition == 0)
//            return 0;
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final JSONArray cities = (JSONArray) getChild(groupPosition, childPosition);
        LayoutInflater infalInflater = null;
        if (convertView == null) {
            infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_covid_data_item_list, null);
        }

//        if (childPosition == 0)
//            return convertView = infalInflater.inflate(R.layout.fragment_covid_data_item, null);

//        ListView listView = (ListView) convertView.findViewById(R.id.listView);
        TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
        TextView cityConfirmed = (TextView) convertView.findViewById(R.id.city_confirmed);
        try {
            cityName.setText(cities.getJSONObject(childPosition).getString("name"));
            cityConfirmed.setText(cities.getJSONObject(childPosition).getJSONObject("detail").getString("confirmed"));
        } catch (JSONException e) {
            System.out.println(childPosition);
            e.printStackTrace();
        }
//        final CityAdapter<JSONObject> adapter = new CityAdapter<JSONObject>(mContext, 0, cities);
//        listView.setAdapter(adapter);

//        TextView txtListChild = (TextView) convertView
//                .findViewById(R.id.question_answer);
//
//        try {
//            txtListChild.setText(cities.getString("confirmed"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        JSONObject stateObj = new JSONObject();
        JSONArray cities = new JSONArray();
        Iterator<String> keys;
        try {
            stateObj = statewise.getJSONObject(groupPosition);
            final JSONObject city = citywise.getJSONObject(stateObj.getString("state")).getJSONObject("districtData");
            keys = city.keys();
            return Iterators.size(keys) + 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        final JSONObject stateObj;
        try {
            return statewise.getJSONObject(groupPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getGroupCount() {
        return this.statewise.length();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        JSONObject stateDetail = (JSONObject) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_covid_data_group, null);
        }

        TextView state = (TextView) convertView
                .findViewById(R.id.state);
        TextView confirmed = (TextView) convertView
                .findViewById(R.id.confirmed);
        TextView recovered = (TextView) convertView
                .findViewById(R.id.recovered);
        TextView decreased = (TextView) convertView
                .findViewById(R.id.decreased);
        TextView active = (TextView) convertView
                .findViewById(R.id.active);
        TextView dateTime = (TextView) convertView
                .findViewById(R.id.date_time);
        state.setTypeface(null, Typeface.BOLD);
        try {
            state.setText(stateDetail.getString("state"));
            confirmed.setText(stateDetail.getString("confirmed"));
            recovered.setText(stateDetail.getString("recovered"));
            decreased.setText(stateDetail.getString("deaths"));
            active.setText(stateDetail.getString("active"));
            dateTime.setText(dateTime(stateDetail.getString("lastupdatedtime")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public String dateTime(String date) {
        String[] dateStr = date.split(" ");
        String[] timeStr = dateStr[1].split(":");
        String newDate = "Updated on: " + dateStr[0] + " Time: " + timeStr[0] + ":" + timeStr[1];
        return newDate;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
