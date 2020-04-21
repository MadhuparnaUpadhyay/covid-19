package live.combatemic.app.Common;

import android.annotation.SuppressLint;
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
import live.combatemic.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class VollyServerCall {

    private String BASE_URL = "https://combatemic.live/api/v1/";
    private static final String TAG = "VollyServerCall";
    private RequestQueue mRequestQueue;

    public void JsonObjectRequest(final Context myContext, final String MAIN_URL, final ServerCallback callback) {
        handleSSLHandshake();
        mRequestQueue = Volley.newRequestQueue(myContext);
        final ProgressDialog pDialog = new ProgressDialog(myContext, R.style.AppCompatAlert);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                BASE_URL + MAIN_URL, null,
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
        handleSSLHandshake();
        mRequestQueue = Volley.newRequestQueue(myContext);
        final ProgressDialog pDialog = new ProgressDialog(myContext, R.style.AppCompatAlert);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(com.android.volley.Request.Method.GET,
                BASE_URL + MAIN_URL, null,
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

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
