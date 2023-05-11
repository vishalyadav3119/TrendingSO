package com.example.trendingso;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("accept_rate")
    int acceptRate;
    @SerializedName("account_id")
    long accountID;
    @SerializedName("display_name")
    String displayName;
    @SerializedName("link")
    String link;
    @SerializedName("profile_image")
    String profileImageURL;
    @SerializedName("reputation")
    int reputation;
    @SerializedName("user_id")
    long userID;
    @SerializedName("user_type")
    String userType;

    public int getAcceptRate() {
        return acceptRate;
    }

    public long getAccountID() {
        return accountID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLink() {
        return link;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public int getReputation() {
        return reputation;
    }

    public long getUserID() {
        return userID;
    }

    public String getUserType() {
        return userType;
    }
}
