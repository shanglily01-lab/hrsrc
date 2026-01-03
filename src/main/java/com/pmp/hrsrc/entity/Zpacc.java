package com.pmp.hrsrc.entity;

import java.io.Serializable;

public class Zpacc implements Serializable {

    public int id ;
    public String zh_name;
    public String zh_pwd;
    public String yx_name;
    public String yx_pwd;
    public String mark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZh_name() {
        return zh_name;
    }

    public void setZh_name(String zh_name) {
        this.zh_name = zh_name;
    }

    public String getZh_pwd() {
        return zh_pwd;
    }

    public void setZh_pwd(String zh_pwd) {
        this.zh_pwd = zh_pwd;
    }

    public String getYx_name() {
        return yx_name;
    }

    public void setYx_name(String yx_name) {
        this.yx_name = yx_name;
    }

    public String getYx_pwd() {
        return yx_pwd;
    }

    public void setYx_pwd(String yx_pwd) {
        this.yx_pwd = yx_pwd;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
