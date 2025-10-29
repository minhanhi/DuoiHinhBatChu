//package com.example.duoihinhbatchugame.api;
//
//import com.example.duoihinhbatchugame.object.CauDo; // Sử dụng class CauDo hiện tại
//import java.util.List;
//import retrofit2.Call;
//import retrofit2.http.GET;
//
//public interface CauHoiApi {
//    // Tên file PHP là layCauHoi.php, chúng ta giả định nó là endpoint
//    // Tuy nhiên, Retrofit cần một BASE URL và một endpoint.
//    // Nếu BASE URL là http://192.168.0.102/duoihinhbatchu/, thì endpoint là layCauHoi.php
//    @GET("layCauHoi.php")
//    Call<List<CauDo>> layTatCaCauHoi();
//}