//package com.example.duoihinhbatchugame.api;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.example.duoihinhbatchugame.DATA;
//import com.example.duoihinhbatchugame.object.CauDo;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import com.squareup.okhttp.ResponseBody;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//public class LayCauHoi extends AsyncTask<Void, Void, Void> {
//
//    String data;
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://192.168.0.102/duoihinhbatchu/layCauHoi.php")
//                .build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            ResponseBody responseBody = response.body();
//            data = responseBody.string();
//        } catch (IOException e) {
//            data = null;
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        if (data != null) {
//            try {
//                DATA.getData().arrCauDo.clear();
//                JSONArray array = new JSONArray(data);
//                for (int i=0;i< array.length();i++){
//                    JSONObject o = array.getJSONObject(i);
//                    CauDo c = new CauDo();
//                    c.anh= o.getString("anh");
//                    c.dapAn= o.getString("dapan");
//                    c.ten= o.getString("ten");
//                    DATA.getData().arrCauDo.add(c);
//                }
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//
//        }
//    }
//}