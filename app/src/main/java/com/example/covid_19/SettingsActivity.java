package com.example.covid_19;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String TAG = "SettingsFragment";

        private Preference thePreference;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            thePreference = findPreference("alarm");

            if (thePreference != null) {
                thePreference.setOnPreferenceChangeListener(this);
            } else {
                Log.d(TAG, "Preference is empty");
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                Toast.makeText(getActivity(), "" + listPreference.getEntries()[index], Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getActivity().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPref.edit();
                myEdit.putString("alarmTime", (String) listPreference.getEntries()[index]);
                myEdit.commit();
                Intent myIntent = new Intent(getActivity(), SettingsActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getActivity(), 001, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
                alarmManager.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, 60*1000, pendingIntent);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 001, new Intent(intentString), PendingIntent.FLAG_UPDATE_CURRENT);
//
//                long startDateTimeMillis = notification.getDateTime().toDateTime().getMillis();
//
//                SettingsActivity.alarmManager().setExact(AlarmManager.RTC_WAKEUP,
//                        startDateTimeMillis,
//                        pendingIntent);
                // Set the summary to reflect the new value.
//                preference.setSummary(
//                        index >= 0
//                                ? listPreference.getEntries()[index]
//                                : null);
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference listPreference = getPreferenceScreen().findPreference(key);
            listPreference.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) listPreference);
        }

    }
}