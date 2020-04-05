package com.example.covid_19.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;

import org.json.JSONObject;

public class VollyServerCall {

    private static final String TAG = "VollyServerCall";
    private RequestQueue mRequestQueue;

    public void JsonObjectRequest(final Context myContext, final String MAIN_URL, final ServerCallback callback) {
        mRequestQueue = Volley.newRequestQueue(myContext);
        final ProgressDialog pDialog = new ProgressDialog(myContext, R.style.AppCompatAlert);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                MAIN_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccess(response);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println(error);
                // hide the progress dialog
                pDialog.hide();
            }
        });
        mRequestQueue.add(jsonObjReq);
    }


}
