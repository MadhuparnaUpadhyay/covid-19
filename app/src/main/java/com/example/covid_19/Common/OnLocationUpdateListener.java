package com.example.covid_19.Common;

import android.location.Location;

public interface OnLocationUpdateListener {
    void onLocationChange(Location location);
    void onError(String error);
}
