package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class Reqmark implements Serializable {
    public int id;
    public int uid;
    public String reqname;
    public String reqdesc;
    public int reqlevel;
    public String reqver;
    public String reqmark;
    public int pid;

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

    public String getReqname() {
        return reqname;
    }

    public void setReqname(String reqname) {
        this.reqname = reqname;
    }

    public String getReqdesc() {
        return reqdesc;
    }

    public void setReqdesc(String reqdesc) {
        this.reqdesc = reqdesc;
    }

    public int getReqlevel() {
        return reqlevel;
    }

    public void setReqlevel(int reqlevel) {
        this.reqlevel = reqlevel;
    }

    public String getReqver() {
        return reqver;
    }

    public void setReqver(String reqver) {
        this.reqver = reqver;
    }

    public String getReqmark() {
        return reqmark;
    }

    public void setReqmark(String reqmark) {
        this.reqmark = reqmark;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
