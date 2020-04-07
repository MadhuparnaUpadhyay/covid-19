package live.combatemic.app.Common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.ColorInt;

import live.combatemic.app.R;


public class CustomSwitchPreference extends SwitchPreference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwitchPreference(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        Switch theSwitch = findSwitchInChildviews((ViewGroup) view);
        if (theSwitch != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                theSwitch.setThumbTintList(Utils.colorToStateList(R.color.colorAccent,
                        R.color.colorAccent));
                theSwitch.setTrackTintList(Utils.colorToStateList(R.color.colorAccent,
                        R.color.colorAccent));

            }
        }

//        TextView titleView = (TextView) view.findViewById(android.R.id.title);
//        titleView.setTextColor(getContext().getResources().getColor(R.color.blue_gray));
    }

    private Switch findSwitchInChildviews(ViewGroup view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            View thisChildview = view.getChildAt(i);
            if (thisChildview instanceof Switch) {
                return (Switch) thisChildview;
            } else if (thisChildview instanceof ViewGroup) {
                Switch theSwitch = findSwitchInChildviews((ViewGroup) thisChildview);
                if (theSwitch != null) return theSwitch;
            }
        }
        return null;
    }

    static class Utils {
        static ColorStateList colorToStateList(@ColorInt int color, @ColorInt int disabledColor) {
            return new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{-android.R.attr.state_checked},
                            new int[]{},
                    },
                    new int[]{
                            disabledColor,
                            disabledColor,
                            color,
                    });
        }
    }
}
