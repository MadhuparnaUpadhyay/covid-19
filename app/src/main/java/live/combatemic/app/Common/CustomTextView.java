package live.combatemic.app.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.DrawableCompat;

import live.combatemic.app.R;

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0);

        if (typedArray.hasValue(R.styleable.CustomTextView_drawableTint)) {
            int color = typedArray.getColor(R.styleable.CustomTextView_drawableTint, 0);

            Drawable[] drawables = getCompoundDrawablesRelative();

            for (Drawable drawable : drawables) {
                if (drawable == null) continue;
                if (Build.VERSION.SDK_INT >= 23) {
                    DrawableCompat.setTint(DrawableCompat.wrap(drawable).mutate(), color);
                } else {
                    drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
            }

            typedArray.recycle();
        }
    }
}
