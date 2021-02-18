package com.community.system.mapper;

import com.community.system.bean.Luntan;
import org.apache.ibatis.annotations.Param;

public interface LuntanMapper extends BaseMapper<Luntan>{

    void update(@Param("replyBody") String replyBody, @Param("id") Integer id);

}
