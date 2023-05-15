package com.example.trendingso;

import com.example.trendingso.data.QuestionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionsAPI {
    @GET("2.3/questions/")
    public Call<QuestionsResponse> getQuestions(@Query("order") String order,
                                                @Query("sort") String sort,
                                                @Query("site") String site,
                                                @Query("filter") String filter,
                                                @Query("key") String key);
    @GET("2.3/search/")
    public Call<QuestionsResponse> searchQuestions(@Query("intitle") String intitle,
                                                @Query("order") String order,
                                                @Query("sort") String sort,
                                                @Query("site") String site,
                                                @Query("filter") String filter,
                                                @Query("key") String key);
}
