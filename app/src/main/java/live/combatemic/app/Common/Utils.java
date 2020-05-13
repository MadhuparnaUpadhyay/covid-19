package live.combatemic.app.Common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    public static void CloseKeyboard(Context context, Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static boolean inViewInBounds(View view, int x, int y) {
        Rect outRect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }
}
