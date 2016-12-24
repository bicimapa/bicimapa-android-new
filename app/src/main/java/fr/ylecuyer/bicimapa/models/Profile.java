package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public class Profile {

    @JsonProperty
    long id;

    @JsonProperty
    String first_name;

    @JsonProperty
    String last_name;

    @JsonProperty
    String avatar_url;

    public long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
