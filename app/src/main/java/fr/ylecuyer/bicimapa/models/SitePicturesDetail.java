package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

@JsonRootName("pictures")
public class SitePicturesDetail {

    @JsonProperty
    double streetview_lat;

    @JsonProperty
    double streetview_lon;

    @JsonProperty
    ArrayList<String> pictures;

    public double getStreetview_lat() {
        return streetview_lat;
    }

    public double getStreetview_lon() {
        return streetview_lon;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    @Override
    public String toString() {
        return "SitePicturesDetail{" +
                "streetview_lat=" + streetview_lat +
                ", streetview_lon=" + streetview_lon +
                ", pictures=" + pictures +
                '}';
    }
}
