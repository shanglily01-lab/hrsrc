package com.pmp.hrsrc.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class WeekReport implements Serializable {

    public int id;
    public int uid;
    @DateTimeFormat(pattern  = "yyyy-MM-dd")
    public Date sdate;
    @DateTimeFormat(pattern  = "yyyy-MM-dd")
    public Date edate;
    public String scontext;
    public String uname;
    public int pid;

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getScontext() {
        return scontext;
    }

    public void setScontext(String scontext) {
        this.scontext = scontext;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
