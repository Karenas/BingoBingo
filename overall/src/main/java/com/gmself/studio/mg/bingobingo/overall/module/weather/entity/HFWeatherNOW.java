package com.gmself.studio.mg.bingobingo.overall.module.weather.entity;

public class HFWeatherNOW {
    private String fl;
    private String tmp;
    private String cond_code;
    private String cond_txt;
    private String hum;
    private String pcpn;
    private String pres;
    private String vis;
    private String cloud;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    public HFWeatherNOW() {
    }

    public String getCloud() {
        return this.cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCond_code() {
        return this.cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_txt() {
        return this.cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getFl() {
        return this.fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return this.hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return this.pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return this.pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return this.tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return this.vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getWind_deg() {
        return this.wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_dir() {
        return this.wind_dir;
    }

    public String getWind_sc() {
        return this.wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getWind_spd() {
        return this.wind_spd;
    }

    public void setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
    }
}
