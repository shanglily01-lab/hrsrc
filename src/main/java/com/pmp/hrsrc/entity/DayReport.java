package com.pmp.hrsrc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class DayReport implements Serializable {

    private int id;
    private int uid;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date sdate;
    private String uname;
    private String scontext;
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getScontext() {
        return scontext;
    }

    public void setScontext(String scontext) {
        this.scontext = scontext;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }



    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
