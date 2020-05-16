package live.combatemic.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import live.combatemic.app.Common.Utils;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        TextView tryAgain = findViewById(R.id.try_again);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
            this.getSupportActionBar().setCustomView(R.layout.custom_toolbar);
            View view = getSupportActionBar().getCustomView();

            ImageView imageView = (ImageView) view.findViewById(R.id.back_button);
            TextView textView = view.findViewById(R.id.toolbar_title);
            textView.setText("Combatemic");

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getConnectivityStatusString(getApplicationContext())) {
                    finish();
                }
            }
        });
    }
}
