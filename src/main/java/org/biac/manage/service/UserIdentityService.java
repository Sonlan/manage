package org.biac.manage.service;

import org.biac.manage.entity.UserIdentity;

/**
 * Created by Song on 2016/7/13.
 */
public interface UserIdentityService {
    /**
     * 根据微信openid查询数据库是否存在该用户
     * @param openid
     * @return
     */
    UserIdentity selectByOpenid(String openid);

    /**
     * 新增一条用户身份信息，成功返回0
     * @param identity
     * @return
     */
    int insert(UserIdentity identity);
}
