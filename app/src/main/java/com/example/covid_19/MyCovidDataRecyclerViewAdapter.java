package com.example.covid_19;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid_19.CovidDataFragment.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            stateDetail = statewise.getJSONObject(position);
            holder.state.setText(stateDetail.getString("state"));
            holder.confirmed.setText(stateDetail.getString("confirmed"));
            holder.recovered.setText(stateDetail.getString("recovered"));
            holder.decreased.setText(stateDetail.getString("deaths"));
            holder.active.setText(stateDetail.getString("active"));
            holder.dateTime.setText(dateTime(stateDetail.getString("lastupdatedtime")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });
    }

    public String dateTime(String date) {
        String[] dateStr = date.split(" ");
        String[] timeStr = dateStr[1].split(":");
        String newDate = "Updated on: " + dateStr[0] + " Time: " + timeStr[0] + ":" + timeStr[1];
        return newDate;
    }

    @Override
    public int getItemCount() {
        return statewise.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView state, confirmed, recovered, decreased, active, dateTime;

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
        }

        @Override
        public String toString() {
            return super.toString() + " '" + state.getText() + "'";
        }
    }
}
