package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class Demark implements Serializable {

    public int id;
    public String sx;
    public String ms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }
}
