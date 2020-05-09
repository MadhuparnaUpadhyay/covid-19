package live.combatemic.app;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import live.combatemic.app.ZoneFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ZoneRecyclerViewAdapter extends RecyclerView.Adapter<ZoneRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final OnListFragmentInteractionListener mListener;

    ZoneRecyclerViewAdapter(JSONArray items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_zone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            JSONObject jsonObject = mValues.getJSONObject(position);
            String location = jsonObject.getString("district");
//            String data = location + " (" + jsonObject.getString("state") + ")";
            String Zone = jsonObject.getString("zone");
            holder.mIdView.setText(location);
            holder.mView.setText(jsonObject.getString("state"));

            GradientDrawable gradientDrawable = (GradientDrawable) holder.mContentView.getBackground().mutate();
            if (Zone.equals("Red")) {
//                holder.mView.setBackgroundColor(Color.parseColor("#FFFF4444"));
                gradientDrawable.setStroke(7, Color.parseColor("#FFFF4444"));
            } else if (Zone.equals("Orange")) {
//                holder.mView.setBackgroundColor(Color.parseColor("#FFFF8800"));
                gradientDrawable.setStroke(7, Color.parseColor("#FFFF8800"));
            } else {
//                holder.mView.setBackgroundColor(Color.parseColor("#FF99CC00"));
                gradientDrawable.setStroke(7, Color.parseColor("#FF99CC00"));
            }
            holder.mContentView.setText(location.charAt(0) + "");
//            holder.mContentView.setText(jsonObject.getString("location"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mView;
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.state);
            mIdView = (TextView) view.findViewById(R.id.district);
            mContentView = (TextView) view.findViewById(R.id.first_char);
        }

    }
}
