package com.pmp.hrsrc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserCommnet implements Serializable {

    public int id;
    public int uid;
    @JsonFormat(pattern  = "yyyy-MM-dd",timezone = "GMT+8")
    public Date co_date;
    public String comment;

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

    public Date getCo_date() {
        return co_date;
    }

    public void setCo_date(Date co_date) {
        this.co_date = co_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
