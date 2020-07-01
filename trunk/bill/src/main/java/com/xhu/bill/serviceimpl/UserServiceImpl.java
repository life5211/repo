package com.xhu.bill.serviceimpl;

import com.xhu.bill.bean.UserBean;
import com.xhu.bill.mapper.UserMapper;
import com.xhu.bill.service.UserService;
import com.xhu.bill.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-13 10:27
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonResult<UserBean> userLogin(UserLoginRequest user) {
        UserBean findOne = userMapper.findOneByUsername(user.getUsername());
        if (findOne == null) {
            return new JsonResult<>(2, "User Not Found");
        }
        if (findOne.getPassword().equals(user.getPassword())) {
            return new JsonResult<>(findOne);
        }
        return new JsonResult<>(2, "Error Password");
    }
}
