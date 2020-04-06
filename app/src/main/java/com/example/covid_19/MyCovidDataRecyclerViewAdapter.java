package com.example.covid_19;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covid_19.CovidDataFragment.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCovidDataRecyclerViewAdapter extends RecyclerView.Adapter<MyCovidDataRecyclerViewAdapter.ViewHolder> {

    private final JSONArray statewise;
    private final JSONObject citywise;
    private final OnListFragmentInteractionListener mListener;

    public MyCovidDataRecyclerViewAdapter(JSONArray statewise, JSONObject citywise, OnListFragmentInteractionListener mListener) {
        this.statewise = statewise;
        this.citywise = citywise;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_covid_data_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject stateDetail = new JSONObject();
        String stateName = null;
        try {
            stateDetail = statewise.getJSONObject(position);
            stateName = stateDetail.getString("state");
            holder.state.setText(stateName);
            holder.confirmed.setText(stateDetail.getString("confirmed"));
            holder.recovered.setText(stateDetail.getString("recovered"));
            holder.decreased.setText(stateDetail.getString("deaths"));
            holder.active.setText(stateDetail.getString("active"));
            holder.dateTime.setText(dateTime(stateDetail.getString("lastupdatedtime")));
            if(stateName.toLowerCase().equals("total")){
                holder.lineText.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalStateName = stateName;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position, finalStateName);
                }
            }
        });
    }

    public String dateTime(String dateString) {
        DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            String newDate = "Updated on: " + new SimpleDateFormat("dd MMM").format(date);
            String time = "Time: " + new SimpleDateFormat("hh:ss a").format(date);
//            String[] dateStr = date.split(" ");
//            String[] timeStr = dateStr[1].split(":");
//            String newDate = "Updated on: " + dateStr[0] + " Time: " + timeStr[0] + ":" + timeStr[1];
            return newDate + ", " + time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return statewise.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView state, confirmed, recovered, decreased, active, dateTime, lineText;
        public LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            state = (TextView) view
                    .findViewById(R.id.state);
            confirmed = (TextView) view
                    .findViewById(R.id.confirmed);
            recovered = (TextView) view
                    .findViewById(R.id.recovered);
            decreased = (TextView) view
                    .findViewById(R.id.decreased);
            active = (TextView) view
                    .findViewById(R.id.active);
            dateTime = (TextView) view
                    .findViewById(R.id.date_time);
            lineText = (TextView) view
                    .findViewById(R.id.line);
            linearLayout = (LinearLayout) view
                    .findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + state.getText() + "'";
        }
    }
}
