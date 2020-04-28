package live.combatemic.app;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import live.combatemic.app.StateDetailsFragment.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StateDetailsRecyclerViewAdapter extends RecyclerView.Adapter<StateDetailsRecyclerViewAdapter.ViewHolder> {

    private final JSONArray statewise;
    private final JSONObject citywise;
    private final OnListFragmentInteractionListener mListener;

    StateDetailsRecyclerViewAdapter(JSONArray statewise, JSONObject citywise, OnListFragmentInteractionListener mListener) {
        this.statewise = statewise;
        this.citywise = citywise;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_state_details_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject stateDetail = null;
        String stateName = null;
        try {
            stateDetail = statewise.getJSONObject(position);
            stateName = stateDetail.getString("state");
            holder.state.setText(stateName);
            holder.confirmed.setText(stateDetail.getString("confirmed"));
            if(!stateDetail.getString("deltaconfirmed").equals("0")) {
                holder.todayConfirmed.setText("(+ " + stateDetail.getString("deltaconfirmed") + " )");
            } else {
                holder.todayConfirmed.setText("");
            }
            holder.recovered.setText(stateDetail.getString("recovered"));
            if(!stateDetail.getString("deltarecovered").equals("0")) {
                holder.todayRecovered.setText("(+ " + stateDetail.getString("deltarecovered") + " )");
            } else {
                holder.todayRecovered.setText("");
            }
            holder.death.setText(stateDetail.getString("deaths"));
            if(!stateDetail.getString("deltadeaths").equals("0")) {
                holder.todayDeath.setText("(+ " + stateDetail.getString("deltadeaths") + " )");
            } else {
                holder.todayDeath.setText("");
            }
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

    private String dateTime(String dateString) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            String newDate = "Updated on: " + new SimpleDateFormat("dd MMM").format(date);
            String time = "Time: " + new SimpleDateFormat("hh:mm a").format(date);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView state, confirmed, recovered, death, active, dateTime, lineText, todayConfirmed, todayRecovered, todayDeath;
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            mView = view;
            state = (TextView) view
                    .findViewById(R.id.state);
            confirmed = (TextView) view
                    .findViewById(R.id.confirmed);
            todayConfirmed = (TextView) view
                    .findViewById(R.id.today_confirmed);
            recovered = (TextView) view
                    .findViewById(R.id.recovered);
            todayRecovered = (TextView) view
                    .findViewById(R.id.today_recovered);
            death = (TextView) view
                    .findViewById(R.id.death);
            todayDeath = (TextView) view
                    .findViewById(R.id.today_death);
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
