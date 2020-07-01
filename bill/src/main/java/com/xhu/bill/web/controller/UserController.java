package com.xhu.bill.web.controller;

import com.xhu.bill.bean.UserBean;
import com.xhu.bill.service.UserService;
import com.xhu.bill.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-9 22:05
 */
@RestController
@RequestMapping("web/user")
@Api(tags = "User_Interface")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录接口doc
     *
     * @param user
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "username,password")
    public ResponseEntity<JsonResult<UserBean>> userLogin(UserService.UserLoginRequest user) {
        JsonResult<UserBean> userResult = userService.userLogin(user);
        if (userResult == null) {
            return new ResponseEntity<>(new JsonResult(1, "ResponseEntity"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

}
