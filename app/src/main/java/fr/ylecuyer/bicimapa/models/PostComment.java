package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostComment {

    @JsonProperty
    String comment;

    @JsonProperty
    String token;

    public PostComment(String comment, String token) {
        this.comment = comment;
        this.token = token;
    }
}
