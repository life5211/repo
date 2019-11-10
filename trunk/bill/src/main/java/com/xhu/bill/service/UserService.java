package com.xhu.bill.service;

import com.xhu.bill.bean.UserBean;
import com.xhu.bill.util.JsonResult;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-9 22:07
 */
public interface UserService {
    /**
     * 用户登录接口
     */
    JsonResult<UserBean> userLogin(UserBean user);
}
