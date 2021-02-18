package com.community.system.mapper;

import com.community.system.bean.User;
import java.util.List;

public interface UserMapper extends BaseMapper<User>{

     User getByName(String name);

     List<User> getuserBySelect(String s);
}
