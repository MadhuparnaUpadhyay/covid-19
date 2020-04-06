package com.example.covid_19.Common;

import org.json.JSONException;
import org.json.JSONObject;

public interface ServerCallback {
    void onSuccess(JSONObject result) throws JSONException;
}
