package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.UserDao;
import com.tydic.work.entity.User;
import com.tydic.work.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-01-18 16:54:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}