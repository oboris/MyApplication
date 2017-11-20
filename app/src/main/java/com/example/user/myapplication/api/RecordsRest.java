package com.example.user.myapplication.api;

import com.example.user.myapplication.model.CPU;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RecordsRest {
    @GET("apis/get_records.php")
//    Call<List<Record>> getRecords(@QueryMap Map<String, String> options);
    Call<List<CPU>> getRecords(@Query("rc") int recordCount, @Query("sp") int startPosition, @Query("fc") int federationCode);
}
