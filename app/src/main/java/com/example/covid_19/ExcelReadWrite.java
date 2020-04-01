package com.example.covid_19;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExcelReadWrite {

    private static final String MAIN_URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=13xSu6SRnOhMyGBiJGw6fncacXIf7X6gelC-L-7I76oA&sheet=emergencies";
    public static final String TAG = "TAG";
    private RequestQueue mRequestQueue;



    public void getDataFromWeb(final RecyclerView view, Context myContext, final ContactListFragment.OnListFragmentInteractionListener mListener) {
        mRequestQueue = Volley.newRequestQueue(myContext);
        final ProgressDialog pDialog = new ProgressDialog(myContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                MAIN_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        // fetch JSONArray named users
                        try {
                            JSONArray userArray = response.getJSONArray("emergencies");
                            System.out.println(userArray);
                            view.setAdapter(new ContactListRecyclerViewAdapter(userArray, mListener));
                        } catch (JSONException e) {
                            System.out.println(e);
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
    }

    private static ExcelModel createExcelModel(String country, String number) {
        return new ExcelModel(country, number);
    }

    public static class ExcelModel {
        public final String country;
        public final String number;

        public ExcelModel(String country, String number) {
            this.country = country;
            this.number = number;
        }

        @Override
        public String toString() {
            return country;
        }
    }

}
