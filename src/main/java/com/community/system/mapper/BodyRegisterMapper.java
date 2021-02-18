package com.community.system.mapper;

import com.community.system.bean.BodyRegister;

import java.util.List;

public interface BodyRegisterMapper extends BaseMapper<BodyRegister> {

    List<BodyRegister> getByLoginUser(String loginUser);

    Integer checkToday(Integer fid);
    Integer checkTem(Integer fid);
}
