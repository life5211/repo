package com.xhu.bill.service;

import com.xhu.bill.bean.UserBean;
import com.xhu.bill.util.JsonResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-9 22:07
 */
public interface UserService {
    /**
     * 用户登录接口
     *
     * @param user
     * @return
     */
    JsonResult<UserBean> userLogin(UserLoginRequest user);

    @Data
    @ApiModel(description = "用户登录参数")
    class UserLoginRequest {
        @ApiModelProperty(value = "用户名", required = true)
        private String username;
        @ApiModelProperty(value = "密码", required = true)
        private String password;
    }
}
