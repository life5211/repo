package com.xhu.bill.web.controller;

import com.xhu.bill.bean.UserBean;
import com.xhu.bill.service.UserService;
import com.xhu.bill.util.JsonResult;
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
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<JsonResult> userLogin(UserBean user) {
        JsonResult<UserBean> userResult = userService.userLogin(user);
        if (userResult == null) {
            return new ResponseEntity<>(new JsonResult(1, "ResponseEntity"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResult, HttpStatus.OK);
    }

}
