package com.community.system.bean;

import java.util.Date;

/**
 * 外出登记
 */
public class OutRegister {

  public Integer id;
  public Integer fid;
  public String context;
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

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
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
