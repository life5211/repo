package com.xhu.bill.mapper;

import com.xhu.bill.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:52
 */
@Mapper
public interface UserMapper {

    int add(UserBean user);

    int delete(String username);

    int update(UserBean user);

    UserBean findOneByUsername(String username);

    UserBean findOneById(int id);

    List<UserBean> find();

    List<UserBean> findUsersByGroup(int id);

    int count();

}
