package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class MeetReport implements Serializable {

    public int id;
    public String mtype;

    public String mdate;
    public String mdesc;
    public String mzg;
    public int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMdesc() {
        return mdesc;
    }

    public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

    public String getMzg() {
        return mzg;
    }

    public void setMzg(String mzg) {
        this.mzg = mzg;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
