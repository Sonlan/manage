package org.biac.manage.service.impl;

import org.biac.manage.dao.UserMapper;
import org.biac.manage.entity.User;
import org.biac.manage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Song on 2016/7/12.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消费者信息删除
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            userDao.deleteById(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"消费者信息删除异常");
            return 1;
        }
    }

    /**
     * 用户挂起
     *
     * @param id
     * @return
     */
    public int suspend(String id) {
        try{
            userDao.suspend(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"消费者信息挂起异常");
            return 1;
        }
    }

    /**
     * 用户恢复激活
     *
     * @param id
     * @return
     */
    public int activate(String id) {
        try{
            userDao.activate(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"消费者信息恢复异常");
            return 1;
        }
    }

    /**
     * 插入一条User记录，成功返回0
     *
     * @param user
     * @return
     */
    public int userInsert(User user) {
        try{
            if(null==userDao.selectByOpenid(user.getOpenid()))
                userDao.insert(user);
            else userDao.updateByOpenid(user);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass()+"新增用户记录失败");
            logger.error(e.toString());
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 更新一条User 记录，成功返回0
     *
     * @param user
     * @return
     */
    public int updateUser(User user) {
        try{
            userDao.updateByOpenid(user);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"消费者信息修改异常");
            return 1;
        }
    }

}
