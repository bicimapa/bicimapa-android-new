package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("stats")
public class SiteStats {

    @JsonProperty
    int comments_count;

    @JsonProperty
    int pictures_count;

    public int getComments_count() {
        return comments_count;
    }

    public int getPictures_count() {
        return pictures_count;
    }

    @Override
    public String toString() {
        return "SiteStats{" +
                "comments_count=" + comments_count +
                ", pictures_count=" + pictures_count +
                '}';
    }
}
