package org.biac.manage.service.impl;

import org.biac.manage.dao.UserIdentityMapper;
import org.biac.manage.entity.UserIdentity;
import org.biac.manage.service.UserIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Song on 2016/7/13.
 */
@Service
public class UserIdentityServiceImpl implements UserIdentityService{
    @Autowired
    private UserIdentityMapper userIdentityDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 新增一条用户身份信息，成功返回0
     *
     * @param identity
     * @return
     */
    public int insert(UserIdentity identity) {
        try {
            UserIdentity userIdentity = userIdentityDao.selectByOpenid(identity.getOpenid());
            if(null == userIdentity)
                userIdentityDao.insert(identity);
            else userIdentityDao.updateByOpenId(identity);
            return  0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"新增用户身份信息失败");
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 根据微信openid查询数据库是否存在该用户
     *
     * @param openid
     * @return
     */
    public UserIdentity selectByOpenid(String openid) {
        try{
            return userIdentityDao.selectByOpenid(openid);
        }catch (Exception e){
            logger.error(this.getClass().toString()+"读取用户身份失败");
            e.printStackTrace();
            return null;
        }
    }
}
