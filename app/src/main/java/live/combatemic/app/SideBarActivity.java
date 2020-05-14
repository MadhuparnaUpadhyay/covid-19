package live.combatemic.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.Utils;
import live.combatemic.app.Common.Version;
import live.combatemic.app.Common.VollyServerCall;


public class SideBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener,
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    int PERMISSION_ID = 44;
    public static final int INSTALL_REPLACE_EXISTING = 0x00000002;
    private static final String TAG = "SideBarActivity";
    static final String NAME_APK_FILE = "combatemic.apk";
    public static final int REQUEST_INSTALL = 0;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private AppBarConfiguration mAppBarConfiguration;
    private TextView nameTextView, emailPhoneTextView;
    private Menu menu;
    private Context context;
    private SharedPreferences sharedPref;
    private TabLayout tabLayout;
    private TextView textView;
    private DrawerLayout drawer;
    private String currentVersion;
    private String newVersion;
    private NavController navController;
    private static final String[] tabArray = {"Statistics", "Video", "Zone"};//Tab title array
    private static final Integer[] tabIcons = {R.drawable.ic_insert_chart_black_24dp, R.drawable.ic_video_library_black_24dp, R.drawable.ic_map_black_24dp};//Tab icons array

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.statistics);

        TextView textViewVersion = findViewById(R.id.version);
        int versionCode = BuildConfig.VERSION_CODE;
        currentVersion = BuildConfig.VERSION_NAME;
        textViewVersion.setText("v" + currentVersion);

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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

        getData();

    }

    private void getData() {
        final String MAIN_URL = "https://combatemic.live";
        final String MAIN_URL_ZONE = "/version";

        VollyServerCall controller = new VollyServerCall(MAIN_URL);
        controller.JsonObjectRequest(this, MAIN_URL_ZONE, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        try {
                            newVersion = response.getString("current");
                            int compare = Version.compare(currentVersion, newVersion);
                            if (compare == -1) {
                                openDownloadDialog(newVersion);
                            } else {
//                                MenuItem nav_item2 = menu.findItem(R.id.update_app);
//                                nav_item2.setVisible(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    void openDownloadDialog(String versionName) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.download_dialog);
        dialog.setCanceledOnTouchOutside(false);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);

        dialog.getWindow().setLayout(width, Toolbar.LayoutParams.WRAP_CONTENT);

        TextView textView = dialog.findViewById(R.id.upgrade_text);
        textView.setText("Upgrade to version v" + versionName);
        // set the custom dialog components - text, image and button
        ImageView image = (ImageView) dialog.findViewById(R.id.close_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogButtonOK);
        TextView dialogCancel = dialog.findViewById(R.id.dialogIgnore);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    downloadApk();
                } else {
                    requestPermissions();
                }
                dialog.dismiss();
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == R.id.action_notifications) {
                View view = MenuItemCompat.getActionView(item);
                if (view != null) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Notification.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_notifications:
                intent = new Intent(this, Notification.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        } else if (id == R.id.update_app) {
            int compare = Version.compare(currentVersion, newVersion);
            if (compare == -1) {
                if (checkPermissions()) {
                    downloadApk();
                } else {
                    requestPermissions();
                }
            } else {
                customAlert(false);
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void customAlert(Boolean str) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!str) {
            dialog.setContentView(R.layout.custom_alert);
        } else {
            dialog.setContentView(R.layout.custom_alert_un);
        }

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);

        dialog.getWindow().setLayout(width, Toolbar.LayoutParams.WRAP_CONTENT);

        TextView textView1 = dialog.findViewById(R.id.text1);

        TextView dialogCancel = dialog.findViewById(R.id.dialogClose);

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
        } else if (position == 1) {
            VideoFragment.newInstance(position);
            textView.setText(R.string.video);
        } else {
            ZoneFragment.newInstance(position);
            textView.setText("Zone");
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
            StateDetailsFragment.newInstance(position);
            textView.setText(R.string.statistics);
        } else if (position == 1) {
            VideoFragment.newInstance(position);
            textView.setText(R.string.video);
        } else {
            ZoneFragment.newInstance(position);
            textView.setText(("Zone"));
        }
        Utils.CloseKeyboard(this, SideBarActivity.this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    void downloadApk() {
        //get destination to update file and set Uri
        //TODO: First I wanted to store my update .apk file on internal storage for my app but apparently android does not allow you to open and install
        //aplication with existing package from there. So for me, alternative solution is Download directory in external storage. If there is better
        //solution, please inform us in comment
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = "combatemic.apk";
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);

        //Delete update file if exists
        final File file = new File(destination);
        if (file.exists())
            //file.delete() - test this, I think sometimes it doesnt work
            file.delete();

        //get url of app on server
        final String url = "https://combatemic.live/combatemic_v0.9.5.apk";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!getPackageManager().canRequestPackageInstalls())
                startActivity(new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName())));
        }
        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        request.setDescription(Main.this.getString(R.string.notification_description));
//        request.setTitle(Main.this.getString(R.string.app_name));

        //set destination
        request.setDestinationUri(uri);

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        assert manager != null;
        final long downloadId = manager.enqueue(request);

        //set BroadcastReceiver to install app when .apk is downloaded
        final String finalDestination = destination;
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    File file = new File(finalDestination);
                    Uri apkURI = FileProvider.getUriForFile(ctxt, BuildConfig.APPLICATION_ID + ".provider", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkURI, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!getPackageManager().canRequestPackageInstalls())
                        customAlert(true);
                }
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                ctxt.startActivity(intent);
                unregisterReceiver(this);
//                finish();
            }
        };
        //register receiver for when .apk download is compete
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadApk();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        int position = mViewPager.getCurrentItem();
        if (position == 0) {
            StateDetailsFragment f = (StateDetailsFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
            f.setupDispatchTouchEvent(event);
        } else if (position == 2) {
            ZoneFragment f = (ZoneFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
            f.setupDispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

}
