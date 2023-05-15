package com.example.trendingso.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("tags")
    List<String> tags;
    @SerializedName("owner")
    Owner owner;
    @SerializedName("comment_count")
    Integer commentCount;
    @SerializedName("reopen_vote_count")
    Integer reopenVoteCount;
    @SerializedName("is_answered")
    Boolean isAnswered;
    @SerializedName("view_count")
    Integer viewCount;
    @SerializedName("favorite_count")
    Integer favoriteCount;
    @SerializedName("down_vote_count")
    Integer downVoteCount;
    @SerializedName("up_vote_count")
    Integer upVoteCount;
    @SerializedName("closed_date")
    Integer closedDate;
    @SerializedName("answer_count")
    Integer answerCount;
    @SerializedName("score")
    Integer score;
    @SerializedName("last_activity_date")
    Integer lastActivityDate;
    @SerializedName("creation_date")
    Long creationDate;
    @SerializedName("last_edit_date")
    Integer lastEditDate;
    @SerializedName("question_id")
    Integer questionId;
    @SerializedName("link")
    String link;
    @SerializedName("title")
    String title;

    public List<String> getTags() {
        return tags;
    }

    public Owner getOwner() {
        return owner;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Integer getReopenVoteCount() {
        return reopenVoteCount;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public Integer getDownVoteCount() {
        return downVoteCount;
    }

    public Integer getUpVoteCount() {
        return upVoteCount;
    }

    public Integer getClosedDate() {
        return closedDate;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getLastActivityDate() {
        return lastActivityDate;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public Integer getLastEditDate() {
        return lastEditDate;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }
}