package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Site {

    @JsonProperty
    long id;

    @JsonProperty
    String name;

    @JsonProperty
    int category_id;

    @JsonProperty
    String category_icon_url;

    @JsonProperty
    String custom_icon_url;

    @JsonProperty
    double longitude;

    @JsonProperty
    double latitude;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_icon_url() {
        return category_icon_url;
    }

    public String getCustom_icon_url() {
        return custom_icon_url;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category_id=" + category_id +
                ", category_icon_url='" + category_icon_url + '\'' +
                ", custom_icon_url='" + custom_icon_url + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String getIconUrl() {

        if (custom_icon_url != null) {
            return custom_icon_url;
        }

        return category_icon_url;
    }
}
