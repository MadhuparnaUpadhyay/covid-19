package live.combatemic.Common;

import android.location.Location;

public interface OnLocationUpdateListener {
    void onLocationChange(Location location);
    void onError(String error);
}
