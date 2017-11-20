package com.example.user.myapplication.api;

import com.example.user.myapplication.model.CPU;

import java.io.IOException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGoodLiftRecords {
//    private static final String HOST = "http://goodlift.ddns.net/";
    private static final String HOST = "http://goodlift.info/";

    private static ApiGoodLiftRecords instance;

    private RecordsRest recordsRest;

    private ApiGoodLiftRecords() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recordsRest = retrofit.create(RecordsRest.class);
    }

    public synchronized static ApiGoodLiftRecords get() {
        if (instance == null) {
            instance = new ApiGoodLiftRecords();
        }
        return instance;
    }

    //rc=50&sp=0&fc=0
    public List<CPU> getRecords(int recordsCount, int startPosition, int federationCode, Callback<List<CPU>> callback) {
        /*Map<String, String> data = new HashMap<>();
        data.put("rc", Integer.toString(recordsCount));
        data.put("sp", Integer.toString(startPosition));
        data.put("fc", Integer.toString(federationCode));*/
        if (null == callback) {
            try {
                return recordsRest.getRecords(recordsCount, startPosition, federationCode).execute().body();
            } catch (IOException e) {
                return null;
            }
        } else {
            recordsRest.getRecords(recordsCount, startPosition, federationCode).enqueue(callback);
            return null;
        }
    }

}
