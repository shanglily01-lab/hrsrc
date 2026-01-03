package com.pmp.hrsrc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class HintCard implements Serializable {

    private int id;
    private int uid;
    private String uname;
    private String ampm; //AM or PM

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String hinttime;

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getHinttime() {
        return hinttime;
    }

    public void setHinttime(String hinttime) {
        this.hinttime = hinttime;
    }

    @Override
    public String toString() {
        return "HintCard{" +
                "id=" + id +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", ampm='" + ampm + '\'' +
                ", hinttime=" + hinttime +
                '}';
    }
}
