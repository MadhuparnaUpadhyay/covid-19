package com.example.covid_19;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;

//import com.fasterxml.jackson.core.JsonFactory;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

//import android.support.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExcelReadWrite {

    private static final String MAIN_URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=16_Y9G03ezjg7A8_AxVX-JkqV6ZplpFsOeFuYdXdPkTw&sheet=companies";

    public static final String TAG = "TAG";

    private static final String KEY_USER_ID = "user_id";

    private static Response response;
    String tag_json_obj = "json_obj_req";
    private RequestQueue mRequestQueue;



    public void getDataFromWeb(final RecyclerView view, Context myContext, final ContactList.OnListFragmentInteractionListener mListener) {
        mRequestQueue = Volley.newRequestQueue(myContext);
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(MAIN_URL)
//                    .build();
//            response = client.newCall(request).execute();
////            System.out.println(response.body().string());
////            Toast.makeText(myContext, "dnfkndfdsgfdhgfhsgklnsl" + response.body().string(), Toast.LENGTH_LONG).show();
//            return new JSONObject(response.body().string());
//        } catch (@NonNull IOException | JSONException e) {
//            Log.e(TAG, "" + e.getLocalizedMessage());
//        }
//        return null;
        final ProgressDialog pDialog = new ProgressDialog(myContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        final JSONObject[] result = new JSONObject[1];
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                MAIN_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        result[0] =response;
                        // fetch JSONArray named users
                        try {
                            JSONArray userArray = response.getJSONArray("companies");
//                            JSONArray userArray = obj.getJSONArray("companies");
                            System.out.println(userArray);
                            view.setAdapter(new MyItemRecyclerViewAdapter(userArray, mListener));
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
        System.out.println(result[0]);
//        return result[0];
    }

    public JSONObject getDataById(int userId) {

//        try {
//            OkHttpClient client = new OkHttpClient();
//
//            RequestBody formBody = new FormEncodingBuilder()
//                    .add(KEY_USER_ID, Integer.toString(userId))
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(MAIN_URL)
//                    .post(formBody)
//                    .build();
//
//            response = client.newCall(request).execute();
//            return new JSONObject(response.body().string());
//
//        } catch (IOException | JSONException e) {
//            Log.e(TAG, "" + e.getLocalizedMessage());
//        }
        return null;
    }

    public ArrayList<ExcelModel> readExcelFileFromAssets(Context myContext) throws IOException {
        ArrayList<ExcelModel> list = new ArrayList<ExcelModel>();
//        try {
//            InputStream myInput;
//            // initialize asset manager
//            AssetManager assetManager = myContext.getAssets();
//            //  open excel sheet
//            myInput = assetManager.open("myexcelsheet.xls");
//            // Create a POI File System object
//            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//            // Create a workbook using the File System
//            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
//            // Get the first sheet from workbook
//            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//            // We now need something to iterate through the cells.
//            Iterator<Row> rowIter = mySheet.rowIterator();
//            int rowno =0;
//            while (((Iterator) rowIter).hasNext()) {
//                HSSFRow myRow = (HSSFRow) rowIter.next();
//                if(rowno !=0) {
//                    Iterator<Cell> cellIter = myRow.cellIterator();
//                    int colno =0;
//                    String sno="", date="", det="";
//                    while (cellIter.hasNext()) {
//                        HSSFCell myCell = (HSSFCell) cellIter.next();
//                        if (colno==0){
//                            sno = myCell.toString();
//                        }else if (colno==1){
//                            date = myCell.toString();
//                        }else if (colno==2){
//                            det = myCell.toString();
//                        }
//                        colno++;
//                    }
//                    list.add(createExcelModel(sno, date));
//                }
//                rowno++;
//            }
//            return list;
//        } catch (Exception e) {
////            Log.e(TAG, "error "+ e.toString());
//        }
//        HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        JacksonFactory factory = JacksonFactory.getDefaultInstance();
//        final Sheets sheetsService = new Sheets.Builder(transport, factory, null)
//                .setApplicationName("My Awesome App")
//                .build();
//        final String spreadsheetId = "0";
//        List<String> ranges = Arrays.asList(
//                "MyFirstSheet!A1:B2",  //replace with your sheet name
//                "Sheet2!A3:B5"
//        );
//        BatchGetValuesResponse result = sheetsService.spreadsheets().values()
//                .batchGet(spreadsheetId)
//                .setRanges(ranges).execute();
//        Log.d("success.", "ranges retrieved: " + result.getValueRanges().size());
////        SpreadsheetService service = new SpreadsheetService("com.banshee");
////        try {
////            // Notice that the url ends
////            // with default/public/values.
////            // That wasn't obvious (at least to me)
////            // from the documentation.
////            String urlString = "https://spreadsheets.google.com/feeds/list/0AsaDhyyXNaFSdDJ2VUxtVGVWN1Yza1loU1RPVVU3OFE/default/public/values";
////
////            // turn the string into a URL
////            URL url = new URL(urlString);
////
////            // You could substitute a cell feed here in place of
////            // the list feed
////            ListFeed feed = service.getFeed(url, ListFeed.class);
////
////            for (ListEntry entry : feed.getEntries()) {
////                CustomElementCollection elements = entry.getCustomElements();
////                String name = elements.getValue("name");
////                System.out.println(name);
////                String number = elements.getValue("Number");
////                System.out.println(number);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (ServiceException e) {
////            e.printStackTrace();
////        }
        return list;
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
