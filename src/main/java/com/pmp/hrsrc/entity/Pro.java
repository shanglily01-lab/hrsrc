package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class Pro  implements Serializable {

    public int id;
    public String pname;
    public String pmark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPmark() {
        return pmark;
    }

    public void setPmark(String pmark) {
        this.pmark = pmark;
    }
}
