package com.example.covid_19;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid_19.ContactListFragment.OnListFragmentInteractionListener;
import com.example.covid_19.model.Contact.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final OnListFragmentInteractionListener mListener;

    public ContactListRecyclerViewAdapter(JSONArray items, OnListFragmentInteractionListener listener) {
        mValues = (JSONArray) items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            JSONObject jsonObject = mValues.getJSONObject(position);
//            holder.mItem = jsonObject.getString("Company");
            holder.state.setText(jsonObject.getString("name"));
//            holder.helpLine.setText(jsonObject.getString("phone"));
//            holder.mIdView1.setText(jsonObject.getString("Fire"));
//            holder.mContentView1.setText(jsonObject.getString("Police"));
            holder.helpLine.setText(jsonObject.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.state);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView state, ambulance, fire, police, helpLine;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            state = (TextView) view.findViewById(R.id.state);
            ambulance = (TextView) view.findViewById(R.id.ambulance);
            fire = (TextView) view.findViewById(R.id.fire);
            police = (TextView) view.findViewById(R.id.police);
            helpLine = (TextView) view.findViewById(R.id.help_line);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + state.getText() + "'";
        }
    }
}
