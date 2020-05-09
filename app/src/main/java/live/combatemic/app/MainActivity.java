package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import live.combatemic.app.Common.CurrentLocationManager;
import live.combatemic.app.Common.OnLocationUpdateListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int PERMISSION_ID = 44;
    private CurrentLocationManager currentLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String state = sharedPref.getString("state", null);

//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//
//                        // Get the Instance ID token//
//                        String token = task.getResult().getToken();
//                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
//                        String msg = getString(R.string.fcm_token, token);
//                        Log.d(TAG, msg);
//                        Log.d(TAG, token);
//                    }
//                });

        if (state == null) {
            currentLocationManager = new CurrentLocationManager(this);
            currentLocationManager.getCurrentLocation(new OnLocationUpdateListener() {
                @Override
                public void onLocationChange(Location location) {
                    Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String error) {
                    Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                // Using handler with postDelayed called runnable run method
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                    startActivity(intent);
                    finish();
                }

            }, 1000); // wait for 1 seconds

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocationManager.getCurrentLocation(new OnLocationUpdateListener() {
                    @Override
                    public void onLocationChange(Location location) {
                        Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Intent intent = new Intent(MainActivity.this, SideBarActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
