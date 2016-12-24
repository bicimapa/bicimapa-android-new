package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("site")
public class SiteDetail {

    @JsonProperty
    long id;

    @JsonProperty
    String name;

    @JsonProperty
    String description;

    @JsonProperty
    int category_id;

    @JsonProperty
    String category_icon_url;

    @JsonProperty
    double latitude;

    @JsonProperty
    double longitude;

    @JsonProperty
    int nb_rating;

    @JsonProperty
    int rating;

    @JsonProperty
    int comments_count;

    @JsonProperty
    int pictures_count;

    @JsonProperty
    String added_by;

    @JsonProperty
    String custom_icon_url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_icon_url() {
        return category_icon_url;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNb_rating() {
        return nb_rating;
    }

    public int getRating() {
        return rating;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getPictures_count() {
        return pictures_count;
    }

    public String getAdded_by() {
        return added_by;
    }

    public String getCustom_icon_url() {
        return custom_icon_url;
    }

    @Override
    public String toString() {
        return "SiteDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                ", category_icon_url='" + category_icon_url + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", nb_rating=" + nb_rating +
                ", rating=" + rating +
                ", comments_count=" + comments_count +
                ", pictures_count=" + pictures_count +
                ", added_by='" + added_by + '\'' +
                ", custom_icon_url='" + custom_icon_url + '\'' +
                '}';
    }

    public String getIconUrl() {

        if (custom_icon_url != null) {
            return custom_icon_url;
        }

        return category_icon_url;
    }
}
