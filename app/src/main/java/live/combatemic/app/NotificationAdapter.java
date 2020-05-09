package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;


public class NotificationAdapter<J> extends ArrayAdapter {

    private final JSONArray notifications;


    NotificationAdapter(Context context, int i, JSONArray result) {
        super(context, i, Collections.singletonList(result));
        this.notifications = result;
    }

    @Override
    public int getCount() {
        super.getCount();
        return notifications.length();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        int count = getCount();
        try {
            return notifications.getJSONObject(count - position - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        JSONObject city = (JSONObject) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_list, parent, false);
        }
        // Lookup view for data population
        TextView notificationTime = convertView.findViewById(R.id.notification_time);
        TextView notificationText = (TextView) convertView.findViewById(R.id.notification_text);
        // Populate the data into the template view using the data object
        try {
            long cityNameValue = city.getLong("timestamp") * 1000;

            @SuppressLint("SimpleDateFormat") DateFormat simple = new SimpleDateFormat("dd MMM, hh:mm: a");
            Date result = new Date(cityNameValue);

            notificationTime.setText(simple.format(result));
            notificationText.setText(city.getString("update"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }

}

