package com.example.covid_19.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;

import org.json.JSONArray;
import org.json.JSONException;
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
//                        pDialog.hide();
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<JSONObject>() {
            @Override
            public void onRequestFinished(Request<JSONObject> request) {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();
            }
        });
    }

    public void JsonArrayRequest(final Context myContext, final String MAIN_URL, final ServerCallbackArray callback) {
        mRequestQueue = Volley.newRequestQueue(myContext);
        final ProgressDialog pDialog = new ProgressDialog(myContext, R.style.AppCompatAlert);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(com.android.volley.Request.Method.GET,
                MAIN_URL, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        pDialog.hide();
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
        mRequestQueue.add(jsonArrReq);
        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<JSONArray>() {
            @Override
            public void onRequestFinished(Request<JSONArray> request) {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();
            }
        });
    }


}
