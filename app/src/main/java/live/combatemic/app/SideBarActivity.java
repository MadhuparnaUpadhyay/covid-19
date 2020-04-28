package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
import androidx.viewpager.widget.ViewPager;

import live.combatemic.app.Common.Utils;

public class SideBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener,
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView nameTextView, emailPhoneTextView;
    private Menu menu;
    private SharedPreferences sharedPref;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private TextView textView;
    private DrawerLayout drawer;
    private NavController navController;
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
        textView.setText(R.string.statistics);

        TextView textViewVersion = findViewById(R.id.version);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        textViewVersion.setText("v" + versionName);

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
        mViewPager.addOnPageChangeListener(this);
        // without this listener the tabs would still get updated when fragments are swiped, but ....  (read the next comment)
        tabLayout.addOnTabSelectedListener(this);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                StateDetailsFragment.newInstance(0).OnDrawerClose();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                Utils.CloseKeyboard(getApplicationContext(), SideBarActivity.this);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.video, R.id.emergency, R.id.qa)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

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
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.emergency) {
            intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else if (id == R.id.qa) {
            intent = new Intent(this, QuestionAnswerActivity.class);
            startActivity(intent);
        } else if (id == R.id.settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.aboutus) {
            intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            default:
                return;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            StateDetailsFragment.newInstance(position);
            textView.setText(R.string.statistics);
        } else {
            VideoFragment.newInstance(position);
            textView.setText(R.string.video);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
        if (position == 0) {
            new StateDetailsFragment();
            textView.setText(R.string.statistics);
        } else {
            VideoFragment.newInstance(position);
            textView.setText(R.string.video);
        }
        Utils.CloseKeyboard(this, SideBarActivity.this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
