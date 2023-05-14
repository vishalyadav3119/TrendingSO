package com.example.trendingso.classes;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.trendingso.classes.Owner;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = false)
    @SerializedName("question_id")
    public Long questionId;
//    @SerializedName("tags")
//    List<String> tags;
    @Embedded
    @SerializedName("owner")
    public Owner owner;
    @SerializedName("comment_count")
    public Integer commentCount;
    @SerializedName("reopen_vote_count")
    public Integer reopenVoteCount;
    @SerializedName("is_answered")
    public Boolean isAnswered;
    @SerializedName("view_count")
    public Integer viewCount;
    @SerializedName("favorite_count")
    public Integer favoriteCount;
    @SerializedName("down_vote_count")
    public Integer downVoteCount;
    @SerializedName("up_vote_count")
    public Integer upVoteCount;
    @SerializedName("closed_date")
    public Integer closedDate;
    @SerializedName("answer_count")
    public Integer answerCount;
    @SerializedName("score")
    public Integer score;
    @SerializedName("last_activity_date")
    public Integer lastActivityDate;
    @SerializedName("creation_date")
    public Integer creationDate;
    @SerializedName("last_edit_date")
    public Integer lastEditDate;
    @SerializedName("link")
    public String link;
    @SerializedName("title")
    public String title;

//    public List<String> getTags() {
//        return tags;
//    }
}