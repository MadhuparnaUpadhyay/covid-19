package com.example.covid_19;

import android.os.Bundle;

import com.example.covid_19.Common.ServerCallback;
import com.example.covid_19.Common.VollyServerCall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouTubeActivity extends AppCompatActivity {

    private ListView listViewVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listViewVideo = findViewById(R.id.list_view_video);
        getVideo();

    }

    private void getVideo() {
        VollyServerCall controller = new VollyServerCall();
        final String MAIN_URL_STATE = "http://combatemic.live/api/v1/covid/videos?location=KR";
        controller.JsonObjectRequest(this, MAIN_URL_STATE, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        JSONArray general = new JSONArray();
                        try {
                            System.out.println(response);
                            System.out.println(response.getJSONArray("general"));
                            general = response.getJSONArray("general");
                            JSONArray location = response.getJSONArray("location");
//                            general.addAll(location);
                            for (int i = 0; i < location.length(); i++) {
                                String jsonObject = location.getString(i);
                                general.put(jsonObject);
                            }
                            final VideoAdapter adapter = new VideoAdapter(YouTubeActivity.this, 0, general);
                            listViewVideo.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }


}
