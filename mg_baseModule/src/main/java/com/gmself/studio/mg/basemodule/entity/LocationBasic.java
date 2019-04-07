package com.gmself.studio.mg.basemodule.entity;

public class LocationBasic {
    private String cid;
    private String location;
    private String parent_city;
    private String admin_area;
    private String cnty;
    private String lat;
    private String lon;
    private String tz;
    private String type;

    public LocationBasic() {
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParent_city() {
        return this.parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }

    public String getAdmin_area() {
        return this.admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getCnty() {
        return this.cnty;
    }


    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTz() {
        return this.tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}