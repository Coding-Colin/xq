package com.community.system.mapper;

import com.community.system.bean.OutRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OutRegisterMapper extends BaseMapper<OutRegister> {

    List<OutRegister> getByLoginUser(String loginUser);

    List<Map<String,Object>> getByLoginUser14(@Param("name") String name);

}
