package live.combatemic.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import live.combatemic.app.Common.CurrentLocationManager;
import live.combatemic.app.Common.OnLocationUpdateListener;

import live.combatemic.app.R;
import com.google.firebase.messaging.FirebaseMessaging;

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
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            Spannable text = new SpannableString(actionBar.getTitle());
//            text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            actionBar.setTitle(text);
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setCustomView(R.layout.custom_toolbar);
            View view = getSupportActionBar().getCustomView();

            ImageView imageView = (ImageView) view.findViewById(R.id.back_button);
            TextView textView = view.findViewById(R.id.toolbar_title);
            textView.setText("Settings");

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
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
        MenuCompat.setGroupDividerEnabled(menu, true);
        return true;
    }


    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String TAG = "SettingsFragment";

        private Preference thePreference, thePreferenceShare, thePreferenceVersion;
        SharedPreferences sharedPref;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            thePreference = findPreference("switch");
            thePreferenceShare = findPreference("share");
            thePreferenceVersion = findPreference("version");

            sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            Boolean value = sharedPref.getBoolean("subscribe", false);

            thePreferenceShare.setOnPreferenceClickListener(this);
            if (thePreference != null) {
                thePreference.setDefaultValue(value);
                thePreference.setOnPreferenceChangeListener(this);
            } else {
                Log.d(TAG, "Preference is empty");
            }

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            thePreferenceVersion.setSummary("v" + versionName + " beta");
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            String key = preference.getKey();
            if (preference instanceof SwitchPreference) {
                subUnsubNot(key, value);
            } else if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                Toast.makeText(getActivity(), "" + listPreference.getEntries()[index], Toast.LENGTH_SHORT).show();

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
                alarmManager.setInexactRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, 60 * 1000, pendingIntent);
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
//            Preference listPreference = getPreferenceScreen().findPreference(key);
//            listPreference.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) listPreference);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            Log.d("TAG", key);
            if (key.equals("share")){
                shareApp();
            }
            return false;
        }

        void subUnsubNot(String key, Object value) {
            // do your work
            Boolean on = (Boolean) value;
            String state = sharedPref.getString("state", null);
            String country = sharedPref.getString("country", null);
            if (state == null) {
                CurrentLocationManager currentLocationManager = new CurrentLocationManager(getActivity());
                currentLocationManager.getCurrentLocation(new OnLocationUpdateListener() {
                    @Override
                    public void onLocationChange(Location location) {
                    }

                    @Override
                    public void onError(String error) {
                    }
                });
            } else {
                if (on) {
                    assert state != null;
                    FirebaseMessaging.getInstance().subscribeToTopic(state.replaceAll("\\s","_"));
                    assert country != null;
                    FirebaseMessaging.getInstance().subscribeToTopic(country.replaceAll("\\s","_"));
                } else {
                    assert state != null;
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(state.replaceAll("\\s","_"));
                    assert country != null;
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(country.replaceAll("\\s","_"));
                }
                SharedPreferences.Editor myEdit = sharedPref.edit();
                myEdit.putBoolean("subscribe", on);
                myEdit.apply();
            }
        }

        void shareApp() {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "Join with us");
            share.putExtra(Intent.EXTRA_TEXT, "https://combatemic.live/");

            getContext().startActivity(Intent.createChooser(share, "Share link!"));
        }
    }
}