package com.omicto;

public class Location {
    public Long locationId;
    public String streetAddress;
    public String postalCode;
    public String city;
    public String stateProvince;
    public String countryId;

    public Location(Long locationId, String streetAddress, String postalCode, String city,
                    String stateProvince, String countryId) {
        this.locationId = locationId;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.stateProvince = stateProvince;
        this.countryId = countryId;
    }
}
