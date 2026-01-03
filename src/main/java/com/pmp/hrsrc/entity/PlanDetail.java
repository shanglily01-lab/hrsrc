package com.pmp.hrsrc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PlanDetail {

    public int id ;
    public int vid;
    public int reqid;
    public String reqname;
    @JsonFormat(pattern  = "yyyy-MM-dd",timezone = "GMT+8")
    public Date sdate;
    @JsonFormat(pattern  = "yyyy-MM-dd",timezone = "GMT+8")
    public Date edate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getReqid() {
        return reqid;
    }

    public void setReqid(int reqid) {
        this.reqid = reqid;
    }

    public String getReqname() {
        return reqname;
    }

    public void setReqname(String reqname) {
        this.reqname = reqname;
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
}
