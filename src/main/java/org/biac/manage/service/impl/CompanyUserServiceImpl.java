package org.biac.manage.service.impl;

import org.biac.manage.dao.CompanyUserMapper;
import org.biac.manage.entity.CompanyUser;
import org.biac.manage.service.CompanyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/7/14.
 */
@Service
public class CompanyUserServiceImpl implements CompanyUserService{
    @Autowired
    private CompanyUserMapper companyUserDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 企业用户登录，成功返回企业用户个人信息，失败返回null
     *
     * @param account
     * @param password
     * @return
     */
    public CompanyUser login(String account, String password) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("account",account);
            map.put("password",password);
            return companyUserDao.login(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"企业用户登录异常");
            return  null;
        }
    }

    /**
     * 企业用户用户挂起
     *
     * @param id
     * @return
     */
    public int suspend(String id) {
        try{
            companyUserDao.suspend(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"用户挂起异常");
            return 1;
        }
    }

    /**
     * 企业用户用户恢复
     *
     * @param id
     * @return
     */
    public int activate(String id) {
        try{
            companyUserDao.activate(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"用户恢复异常");
            return 1;
        }
    }

    /**
     * 删除操作
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            companyUserDao.delete(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"用户删除异常");
            return 1;
        }
    }

    /**
     * 用户信息编辑
     *
     * @param companyUser
     * @return
     */
    public int edit(CompanyUser companyUser) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("record",companyUser);
            companyUserDao.updateSelective(map);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"用户修改异常");
            return 1;
        }
    }

    /**
     * 根据账户名与权限分页查找用户
     *
     * @param account
     * @param authority
     * @param page
     * @return
     */
    public List<CompanyUser> query(String account, String authority, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("account",account);
            map.put("authority",authority);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return companyUserDao.query(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"企业用户查询异常");
            return  null;
        }
    }

    /**
     * 返回符合查询条件的记录数目
     *
     * @param account
     * @param authority
     * @return
     */
    public int queryForSize(String account, String authority) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("account",account);
            map.put("authority",authority);
            return companyUserDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"企业用户数目查询异常");
            return  0;
        }
    }
}
