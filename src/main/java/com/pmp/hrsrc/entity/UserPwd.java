package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class UserPwd  implements Serializable {

    String uname;
    String npd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getNpd() {
        return npd;
    }

    public void setNpd(String npd) {
        this.npd = npd;
    }
}
