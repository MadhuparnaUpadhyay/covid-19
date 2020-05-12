package live.combatemic.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StateDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private CardView cardView;
    private RecyclerView recyclerView;
    private StateDetailsFragment.OnListFragmentInteractionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(stateName);
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setCustomView(R.layout.custom_toolbar);
            View view = getSupportActionBar().getCustomView();


            recyclerView = (RecyclerView) findViewById(R.id.list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);

            mListener = new StateDetailsFragment.OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(int position, JSONObject finalStateDetail) {
                    StateDetailFragment fragmentDemos = (StateDetailFragment)
                            getSupportFragmentManager().findFragmentById(R.id.current);
                    fragmentDemos.setData(finalStateDetail, new JSONObject());
                    try {
                        textView.setText(finalStateDetail.getString("state"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            ImageView imageView = (ImageView) view.findViewById(R.id.back_button);
            textView = view.findViewById(R.id.toolbar_title);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        Intent intent = getIntent();

        cardView = findViewById(R.id.creativity);
        cardView.setOnClickListener(this);

        StateDetailFragment fragmentDemos = (StateDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.current);
        try {
            JSONObject jsonCity = new JSONObject(intent.getStringExtra("city"));
            JSONObject jsonState = new JSONObject(intent.getStringExtra("state"));
            JSONArray jsonArray = new JSONArray(intent.getStringExtra("states"));

            recyclerView.setAdapter(new StateListAdapter(jsonArray, new JSONObject(), mListener));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(false);

            textView.setText(jsonState.getString("state"));

            fragmentDemos.setData(jsonState, jsonCity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == cardView) {
            Intent intent = new Intent(this, WorkingActivity.class);
            startActivity(intent);
        }
    }

}
