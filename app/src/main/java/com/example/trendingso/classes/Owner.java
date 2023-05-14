package com.example.trendingso.classes;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("accept_rate")
    public int acceptRate;
    @SerializedName("account_id")
    public long accountID;
    @SerializedName("display_name")
    public String displayName;
    @SerializedName("link")
    public String ownerLink;
    @SerializedName("profile_image")
    public String profileImageURL;
    @SerializedName("reputation")
    public int reputation;
    @SerializedName("user_id")
    public long userID;
    @SerializedName("user_type")
    public String userType;
}
