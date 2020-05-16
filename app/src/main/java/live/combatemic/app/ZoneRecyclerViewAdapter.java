package live.combatemic.app;

import androidx.annotation.NonNull;
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

import live.combatemic.app.ZoneListFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_zone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        JSONObject jsonObject = null;
        try {
            jsonObject = mValues.getJSONObject(position);
            String district = jsonObject.getString("district");
            String Zone = jsonObject.getString("zone");
            holder.district.setText(district);
            holder.state.setText(jsonObject.getString("state"));

            GradientDrawable gradientDrawable = (GradientDrawable) holder.firstChar.getBackground().mutate();
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
            holder.firstChar.setText(district.charAt(0) + "");
//            holder.mContentView.setText(jsonObject.getString("location"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJsonObject = jsonObject;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(finalJsonObject);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView state;
        final TextView district;
        final TextView firstChar;

        ViewHolder(View view) {
            super(view);
            mView = view;
            state = view.findViewById(R.id.state);
            district = (TextView) view.findViewById(R.id.district);
            firstChar = (TextView) view.findViewById(R.id.first_char);
        }

    }
}
