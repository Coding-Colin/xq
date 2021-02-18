package com.community.system.mapper;

import com.community.system.bean.LuntanReply;
import java.util.List;

public interface LuntanReplyMapper extends BaseMapper<LuntanReply>{

    List<LuntanReply> getByRid(Integer rid);
}
