package live.combatemic.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import live.combatemic.app.Common.ServerCallbackArray;
import live.combatemic.app.Common.VollyServerCall;

public class NotificationActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        listView = (ListView) findViewById(R.id.notification_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText("Notifications");
        ImageView imageView = (ImageView) findViewById(R.id.back_button);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();

    }


    void getData() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_NOTIFICATION = "/updatelog/log.json";
        controller.JsonArrayRequest(this, MAIN_URL_NOTIFICATION, new ServerCallbackArray() {
                    @Override
                    public void onSuccess(JSONArray result) throws JSONException {
                        final NotificationAdapter<JSONArray> adapter = new NotificationAdapter<JSONArray>(getApplicationContext(), 0, result);
                        listView.setAdapter(adapter);
                    }
                }
        );
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

}
