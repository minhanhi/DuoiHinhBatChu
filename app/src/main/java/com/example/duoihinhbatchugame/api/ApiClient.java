//package com.example.duoihinhbatchugame.api;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ApiClient {
//    // CHÚ Ý: Đổi IP này thành IP cục bộ của bạn (hoặc IP công cộng nếu có)
//    private static final String BASE_URL = "http://192.168.0.102/duoihinhbatchu/";
//    private static ApiClient instance;
//    private CauHoiApi cauHoiApi;
//
//    public static ApiClient getInstance() {
//        if (instance == null) {
//            instance = new ApiClient();
//        }
//        return instance;
//    }
//
//    private ApiClient() {
//        // Cần OkHttp để Retrofit hoạt động, nhưng GsonConverterFactory là đủ cho ví dụ này.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        cauHoiApi = retrofit.create(CauHoiApi.class);
//    }
//
//    public static CauHoiApi getCauHoiApi() {
//        return getInstance().cauHoiApi;
//    }
//}