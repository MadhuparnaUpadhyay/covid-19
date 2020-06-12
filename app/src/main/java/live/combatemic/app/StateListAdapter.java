package live.combatemic.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder> {

    private final JSONArray statewise;
    private final JSONObject citywise;
    private final StateDetailsFragment.OnListFragmentInteractionListener mListener;

    StateListAdapter(JSONArray statewise, JSONObject citywise, StateDetailsFragment.OnListFragmentInteractionListener mListener) {
        try {
            JSONObject stateDetail = statewise.getJSONObject(0);
            String stateName = stateDetail.getString("state");
            if (stateName.toLowerCase().equals("total")) {
                statewise.remove(0);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.statewise = statewise;
        this.citywise = citywise;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public StateListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.state_name_list_fragment, parent, false);
        return new StateListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StateListAdapter.ViewHolder holder, final int position) {
        JSONObject stateDetail = null;
        String stateName = null;
        String statecode = null;
        int stateConfirmed = 0;
        try {
            stateDetail = statewise.getJSONObject(position);
            stateName = stateDetail.getString("state");
            statecode = stateDetail.getString("statecode");
            stateConfirmed = Integer.parseInt(stateDetail.getString("confirmed"));
            holder.state.setText(stateName);
            holder.confirmed.setText(stateDetail.getString("deltaconfirmed"));
            if (stateName.toLowerCase().equals("total")) {
                holder.mView.setVisibility(View.GONE);
            } else {
                holder.mView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalStateName = stateName;
        final String finalStatecode = statecode;
        final JSONObject finalStateDetail = stateDetail;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position, finalStateDetail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statewise.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView state, confirmed;
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            mView = view;
            state = (TextView) view
                    .findViewById(R.id.state_name);
            confirmed = (TextView) view
                    .findViewById(R.id.state_confirmed);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + state.getText() + "'";
        }
    }
}
