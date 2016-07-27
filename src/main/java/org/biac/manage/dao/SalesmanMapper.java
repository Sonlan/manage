package org.biac.manage.dao;


import org.biac.manage.entity.Salesman;

import java.util.List;
import java.util.Map;

public interface SalesmanMapper {
    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    Salesman selectById(String id);

    /**
     * 将用户status置为1，挂起
     *
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 将用户status置为0，恢复
     *
     * @param id
     * @return
     */
    int activate(String id);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 编辑用户信息
     *
     * @param map
     * @return
     */
    int updateSelective(Map<Object, Object> map);

    /**
     * 分页查询当前经销商用户下的推销员账户
     * @param map
     * @return
     */
    List<Salesman> querySelective(Map<Object,Object> map);

    /**
     * 查询当前经销商下符合查询条件的推销员人数
     * @param map
     * @return
     */
    List<Salesman> queryForSize(Map<Object,Object> map);

    int insertSelective(Salesman salesman);
}