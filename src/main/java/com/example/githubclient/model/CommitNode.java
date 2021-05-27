package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CommitNode implements Serializable {


    @JsonProperty("commit")
    @SerializedName("commit")
    private Commit commit;
    @JsonProperty("url")
    @SerializedName("url")
    private String url;
    @JsonProperty("sha")
    @SerializedName("sha")
    private String sha;


    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Date getDate() {
        return this.getCommit().getAuthor().getDate();
    }
}
