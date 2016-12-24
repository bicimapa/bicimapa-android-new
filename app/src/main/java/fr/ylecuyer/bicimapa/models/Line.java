package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Line {

    @JsonProperty
    long id;

    @JsonProperty
    String color;

    @JsonProperty
    ArrayList<ArrayList<Double>> path;

    public long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<ArrayList<Double>> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", path=" + path +
                '}';
    }
}
