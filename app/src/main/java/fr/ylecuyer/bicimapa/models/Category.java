package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    @JsonProperty
    long id;

    @JsonProperty
    boolean is_public;

    @JsonProperty
    boolean is_initial;

    @JsonProperty
    String name;

    @JsonProperty
    String variety;

    @JsonProperty
    String icon_path;

    @JsonProperty
    String color;

    public boolean is_initial() {
        return is_initial;
    }

    public long getId() {
        return id;
    }

    public boolean is_public() {
        return is_public;
    }

    public String getName() {
        return name;
    }

    public String getVariety() {
        return variety;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public String getColor() {
        return color;
    }
}
