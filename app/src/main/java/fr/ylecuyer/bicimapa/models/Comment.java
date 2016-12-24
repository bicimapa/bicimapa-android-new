package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {

    @JsonProperty
    String comment;

    @JsonProperty
    String created_at;

    @JsonProperty
    String added_by;

    @JsonProperty
    String avatar_url;

    public String getComment() {
        return comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getAdded_by() {
        return added_by;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", created_at='" + created_at + '\'' +
                ", added_by='" + added_by + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
