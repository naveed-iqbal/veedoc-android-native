package com.veemed.veedoc.models;

public class Location {
    private String zipCode;
    private String country;
    private String state;
    private String city;
    private String address;
    private String otherAddress;

    public Location(String zipCode, String country, String state, String city, String address) {
        this.zipCode = zipCode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
    }

    public Location(String zipCode, String country, String state, String address) {
        this.zipCode = zipCode;
        this.country = country;
        this.state = state;
        this.address = address;
    }



    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }
}
