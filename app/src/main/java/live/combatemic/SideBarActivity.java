package live.combatemic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import live.combatemic.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SideBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener,
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView nameTextView, emailPhoneTextView;
    private Menu menu;
    private SharedPreferences sharedPref;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private TextView textView;
    private static final String[] tabArray = {"Statistics", "Video"};//Tab title array
    private static final Integer[] tabIcons = {R.drawable.ic_insert_chart_black_24dp, R.drawable.ic_video_library_black_24dp};//Tab icons array

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Statistics");
//        getSupportActionBar().setTitle("sfbsfhj");
//
//        sharedPref = this.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        sharedPref.registerOnSharedPreferenceChangeListener(this);
//        String name = sharedPref.getString("name", null);
//        String email = sharedPref.getString("email", null);
//        String phone = sharedPref.getString("phone", null);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setUpCustomTabs();
        // this is done "so that the tab position is kept in sync"
        // what it does is when you swipe the fragment in view pager, it updates the tabs
//        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(this);
        // without this listener the tabs would still get updated when fragments are swiped, but ....  (read the next comment)
        tabLayout.addOnTabSelectedListener(this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();
//        nameTextView = header.findViewById(R.id.username);
//        emailPhoneTextView = header.findViewById(R.id.email_phone);
//        if (name != null) {
//            menu.getItem(0).setTitle("Profile");
//            nameTextView.setText(name);
//        }
//        if (email != null) {
//            emailPhoneTextView.setText(email);
//        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.video, R.id.emergency, R.id.qa)
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

//        Intent intent = new Intent(this, LocationTrack.class);
//        startService(intent);
//
//        schedulePeriodicWork(navigationView);
//        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
//        System.out.println("calling Alarm receiver ");
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        // set unique id to the pending item, so we can call it when needed
//        PendingIntent pi = PendingIntent.getBroadcast(this, 001, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
//        System.out.println("" + SystemClock.elapsedRealtime() + "sdba" + AlarmManager.INTERVAL_HALF_HOUR);
//        assert alarmManager != null;
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60*1000, 60*1000, pi);
    }

    private void setUpCustomTabs() {
        for (int i = 0; i < tabIcons.length; i++) {
            TextView customTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);//get custom view
            customTab.setText(tabArray[i]);//set text over view
            customTab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);//set icon above the view
            TabLayout.Tab tab = tabLayout.getTabAt(i);//get tab via position
            if (tab != null)
                tab.setCustomView(customTab);//set custom view
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.side_bar, menu);
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
        if (id == R.id.video) {
//            Fragment fragment = new RegistrationProfileFragment();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
//            finish();
        } else if (id == R.id.emergency) {
//            Fragment fragment = new ContactList();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else if (id == R.id.qa) {
//            Fragment fragment = new QuestionAnswer();
//            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            intent = new Intent(this, QuestionAnswerActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.aboutus) {
            intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void schedulePeriodicWork(View v) {
        setupCouponRefreshPeriodicTask();
    }

    private void setupCouponRefreshPeriodicTask() {

        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(this);

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        menu.getItem(0).setTitle(tabsTitles[position]);
        if (position == 0) {
            new CovidDataFragment();
            textView.setText("Statistics");
        } else {
            VideoFragment.newInstance(position);
            textView.setText("Video");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final Integer position = tab.getPosition();
        mViewPager.setCurrentItem(position);
        if (position == 0) {
            new CovidDataFragment();
            textView.setText("Statistics");
        } else {
            VideoFragment.newInstance(position);
            textView.setText("Video");
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void callOnTabSelectedManually(){
        mViewPager.setCurrentItem(3);
    }
}
