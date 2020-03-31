package com.example.covid_19;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.emergency.EmergencyNumber;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SideBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView nameTextView, emailPhoneTextView;
    private Menu menu;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, ""+toolbar, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("sfbsfhj");

        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        String name = sharedPref.getString("name", null);
        String email = sharedPref.getString("email", null);
        String phone = sharedPref.getString("phone", null);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();
        nameTextView = header.findViewById(R.id.username);
        emailPhoneTextView = header.findViewById(R.id.email_phone);
        if(name != null) {
            menu.getItem(0).setTitle("Profile");
            nameTextView.setText(name);
        }
        if(email != null){
            emailPhoneTextView.setText(email);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.profile, R.id.emergency, R.id.qa)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);

//        if (name != null) {
//            graph.setStartDestination(R.id.maps);
//            navController.setGraph(graph);
//        } else {
//            graph.setStartDestination(R.id.profile);
//            navController.setGraph(graph);
//        }

        Intent intent = new Intent(this, LocationTrack.class);
        startService(intent);

        schedulePeriodicWork(navigationView);
//        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
//        System.out.println("calling Alarm receiver ");
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        // set unique id to the pending item, so we can call it when needed
//        PendingIntent pi = PendingIntent.getBroadcast(this, 001, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
//        System.out.println("" + SystemClock.elapsedRealtime() + "sdba" + AlarmManager.INTERVAL_HALF_HOUR);
//        alarmManager.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR, pi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();//declaring Fragment Manager
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();// declaring Fragment Transaction

        Intent intent;
        if (id == R.id.profile) {
//            Fragment fragment = new RegistrationProfileFragment();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent=new Intent(this, MainActivity.class);
            startActivity(intent);
//            finish();
        } else if (id == R.id.emergency) {
//            Fragment fragment = new ContactList();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent=new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else if (id == R.id.qa) {
//            Fragment fragment = new QuestionAnswer();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent=new Intent(this, QuestionAnswerActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            intent=new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void schedulePeriodicWork(View v){
        setupCouponRefreshPeriodicTask();
    }

    private void setupCouponRefreshPeriodicTask(){

        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(this);

        Toast.makeText(this, "" + preferences.getBoolean("refreshTask", false), Toast.LENGTH_SHORT).show();
        //schedule recurring task only once
//        if(!preferences.getBoolean("refreshTask", false)){
            refreshCouponPeriodicWork();
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("refreshTask", true);
//            editor.commit();
//        }
    }

    public void refreshCouponPeriodicWork() {

        //define constraints
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();

        Data source = new Data.Builder()
                .putString("workType", "PeriodicTime")
                .build();

        PeriodicWorkRequest refreshCpnWork =
                new PeriodicWorkRequest.Builder(AlarmWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(myConstraints)
                        .setInputData(source)
                        .build();

        Toast.makeText(this, "th m,nf,dsf ", Toast.LENGTH_SHORT).show();
        WorkManager.getInstance().enqueue(refreshCpnWork);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String value = sharedPreferences.getString(key, null);
        switch (key) {
            case "name":
                nameTextView.setText(value);
                menu.getItem(0).setTitle("Profile");
                return;
            case "email":
                emailPhoneTextView.setText(value);
                return;
//            case "phone":
//                emailPhoneTextView.setText(email);
//                return;
        }
    }
}
