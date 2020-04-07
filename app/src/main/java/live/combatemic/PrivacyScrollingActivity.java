package live.covid_19;

import android.os.Bundle;

import com.example.covid_19.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PrivacyScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Privacy Policy");
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
////            actionBar.setDisplayHomeAsUpEnabled(true);
////            Spannable text = new SpannableString(actionBar.getTitle());
////            text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
////            actionBar.setTitle(text);
//            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            this.getSupportActionBar().setDisplayShowCustomEnabled(true);
//            this.getSupportActionBar().setCustomView(R.layout.custom_toolbar);
//            View view = getSupportActionBar().getCustomView();
//
//            ImageView imageView = (ImageView) view.findViewById(R.id.back_button);
//            TextView textView = view.findViewById(R.id.toolbar_title);
//            textView.setText("Privacy Policy");
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
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
}
