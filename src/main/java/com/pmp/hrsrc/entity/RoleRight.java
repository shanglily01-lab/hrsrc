package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class RoleRight implements Serializable {
    public int id ;
    public int rid;
    public int rgid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getRgid(){return rgid;}
    public void setRgid(int rgid) {
        this.rgid = rgid;
    }
}
