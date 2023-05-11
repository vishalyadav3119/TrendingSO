package com.example.trendingso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionsAPI {
    @GET("2.3/questions/")
    public Call<JsonResponse> getQuestions(@Query("fromdate") long fromDate,
                                             @Query("todate") long toDate,
                                             @Query("order") String order,
                                             @Query("sort") String sort,
                                             @Query("site") String site,
                                             @Query("key") String key);
}
