package com.example.trendingso.data;

import com.example.trendingso.classes.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionsAPI {
    @GET("2.3/questions/")
    public Call<JsonResponse> getQuestions(@Query("order") String order,
                                           @Query("sort") String sort,
                                           @Query("site") String site,
                                           @Query("filter") String filter,
                                           @Query("key") String key);
}
