package com.example.trendingso;

import android.util.Log;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitInstance {
    private static final String TAG = "RetroFitInstance";
    private static QuestionsAPI questionsAPI;
    private RetroFitInstance(){};
    public static QuestionsAPI getInstance(){
        if(questionsAPI == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder()
                                .header("Content-Encoding", "gzip")
                                .header("Content-Type", "application/json")
                                .build();
                    });
            OkHttpClient client = builder.build();
            questionsAPI = new Retrofit.Builder()
                    .baseUrl("https://api.stackexchange.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(QuestionsAPI.class);
        }
        return questionsAPI;
    }
}
