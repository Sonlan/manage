package org.biac.manage.service.impl;

import org.biac.manage.dao.AreaMapper;
import org.biac.manage.entity.Area;
import org.biac.manage.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Song on 2016/8/2.
 */
@Service
public class AreaServiceImpl implements AreaService{
    @Autowired
    private AreaMapper areaDao;

    public List<Area> queryByCode(String code) {
        try{
            return areaDao.queryByCode(code);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
