package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReviewComment {

    @SerializedName("body")
    @JsonProperty("body")
    private String body;

    @JsonProperty("created_at")
    @SerializedName("created_at")
    private Date creationDate;

    @JsonProperty("updated_at")
    @SerializedName("updated_at")
    private Date updatingDate;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatingDate() {
        return updatingDate;
    }

    public void setUpdatingDate(Date updatingDate) {
        this.updatingDate = updatingDate;
    }
}
