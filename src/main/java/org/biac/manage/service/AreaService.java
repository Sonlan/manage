package org.biac.manage.service;

import org.biac.manage.entity.Area;

import java.util.List;

/**
 * Created by Song on 2016/8/2.
 */
public interface AreaService {
    List<Area> queryByCode(String code);
}
