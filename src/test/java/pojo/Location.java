package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Location {
    private String postcode;
    private String country;
    private String countryabbreviation;
    private List<Place> places;

    @JsonProperty("post code")
    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    @JsonProperty("country abbreviation")
    public String getCountryabbreviation() {
        return countryabbreviation;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryabbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Location{" +
                "postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", countryabbreviation='" + countryabbreviation + '\'' +
                ", places=" + places +
                '}';
    }
}
