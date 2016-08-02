package org.biac.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.biac.manage.entity.Area;

import java.util.List;

public interface AreaMapper {

    List<Area> queryByCode(@Param(value = "code") String code);
}