package com.community.system.mapper;

import com.community.system.bean.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoMapper extends BaseMapper<Info>{

    void reply(@Param("id") Integer id, @Param("info") String info);

    List<Info> getByLoginUser(String loginUser);
}
