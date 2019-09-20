package com.omicto;

public class Country {
    public String countryId;
    public String countryName;
    public Long regionId;

    public Country(String countryId, String countryName, Long regionId) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.regionId = regionId;
    }
}
