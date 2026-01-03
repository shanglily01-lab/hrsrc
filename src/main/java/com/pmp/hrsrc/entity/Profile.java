package com.pmp.hrsrc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Profile  implements Serializable {

    private int id;
    private int uid;
    private String uname;
    private String email;
    private String tg;
    private String whatsapp;
    private String phone;
    private String addr;
    private String saddr;
    private String vaddr;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date udate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSaddr() {
        return saddr;
    }

    public void setSaddr(String saddr) {
        this.saddr = saddr;
    }

    public String getVaddr() {
        return vaddr;
    }

    public void setVaddr(String vaddr) {
        this.vaddr = vaddr;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", tg='" + tg + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", saddr='" + saddr + '\'' +
                ", vaddr='" + vaddr + '\'' +
                ", udate=" + udate +
                '}';
    }
}
