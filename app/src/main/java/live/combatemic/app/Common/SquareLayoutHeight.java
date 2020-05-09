package live.combatemic.app.Common;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SquareLayoutHeight extends LinearLayout {

    public SquareLayoutHeight(Context context) {
        super(context);
    }

    public SquareLayoutHeight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayoutHeight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SquareLayoutHeight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        // or you can use this if you want the square to use height as it basis
        // super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
