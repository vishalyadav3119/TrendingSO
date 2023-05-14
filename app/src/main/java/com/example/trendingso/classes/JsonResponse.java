package com.example.trendingso.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponse {
    @SerializedName("items")
    List<Question> questions;
    @SerializedName("has_more")
    Boolean hasMore;
    @SerializedName("quota_max")
    Integer quotaMax;
    @SerializedName("quota_remaining")
    Integer quotaRemaining;

    public List<Question> getQuestions() {
        return questions;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }
}
