package live.combatemic.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CityExpandableListAdapter extends BaseExpandableListAdapter {

    private final JSONArray citywise;
    private final Context mContext;

    CityExpandableListAdapter(Context mContext, JSONArray citywise) {
        this.mContext = mContext;
        this.citywise = citywise;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        try {
            return citywise.getJSONObject(groupPosition).getJSONObject("detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final JSONObject cityDetail = (JSONObject) getChild(groupPosition, childPosition);
        LayoutInflater infalInflater = null;
        if (convertView == null) {
            infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.city_list_group_item, null);
        }

        TextView confirmed = (TextView) convertView.findViewById(R.id.city_confirmed);
        TextView cityRecovered = (TextView) convertView.findViewById(R.id.city_recovered);
        TextView cityActive = (TextView) convertView.findViewById(R.id.city_active);
        TextView cityDeath = (TextView) convertView.findViewById(R.id.city_death);
        TextView cityToRecovered = (TextView) convertView.findViewById(R.id.today_recovered);
        TextView cityToDeath = (TextView) convertView.findViewById(R.id.today_death);
        TextView cityToConfi = (TextView) convertView.findViewById(R.id.today_confirmed);
        try {
            confirmed.setText(cityDetail.getString("confirmed"));
            cityRecovered.setText(cityDetail.getString("recovered"));
            cityActive.setText(cityDetail.getString("active"));
            cityDeath.setText(cityDetail.getString("deceased"));
            cityToConfi.setText(cityDetail.getJSONObject("delta").getString("confirmed"));
            cityToRecovered.setText(cityDetail.getJSONObject("delta").getString("recovered"));
            cityToDeath.setText(cityDetail.getJSONObject("delta").getString("deceased"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        try {
            return citywise.getJSONObject(groupPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public int getGroupCount() {
        return this.citywise.length();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        JSONObject cityDetail = (JSONObject) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.city_list_group, null);
        }

        TextView city = (TextView) convertView
                .findViewById(R.id.city_name);
        ImageView upImage = (ImageView) convertView
                .findViewById(R.id.expandable_up);
        ImageView downImage = (ImageView) convertView
                .findViewById(R.id.expandable_down);

        if (isExpanded) {
            upImage.setVisibility(View.VISIBLE);
            downImage.setVisibility(View.GONE);
        } else {
            upImage.setVisibility(View.GONE);
            downImage.setVisibility(View.VISIBLE);
        }

        try {
            String cityNameValue = cityDetail.getString("name") + "";
            city.setText(cityNameValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
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
