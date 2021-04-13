package com.community.system.mapper;

import com.community.system.bean.Affiche;

import java.util.List;

public interface AfficheMapper extends BaseMapper<Affiche>{

	List<Affiche> queryByKeyWord(String keyWord);

}
