package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class RoleRightDAO implements Serializable {
    public int id;
    public String rname;
    public String rgname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRgname() {
        return rgname;
    }

    public void setRgname(String rgname) {
        this.rgname = rgname;
    }
}
