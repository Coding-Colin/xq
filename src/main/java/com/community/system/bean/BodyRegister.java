package com.community.system.bean;

import java.util.Date;

/**
 * 体温登记
 */
public class BodyRegister {

  public Integer id;
  public Integer fid;
  public float temperature;
  public Date date;
  public String loginUser;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getFid() {
    return fid;
  }

  public void setFid(Integer fid) {
    this.fid = fid;
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getLoginUser() {
    return loginUser;
  }

  public void setLoginUser(String loginUser) {
    this.loginUser = loginUser;
  }
}
