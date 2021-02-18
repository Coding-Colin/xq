package com.community.system.mapper;

import com.community.system.bean.Family;

import java.util.List;

public interface FamilyMapper extends BaseMapper<Family> {

  List<Family> getByLoginUser(String loginUser);
}
