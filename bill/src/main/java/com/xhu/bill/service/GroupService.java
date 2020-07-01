package com.xhu.bill.service;

import com.xhu.bill.bean.GroupBean;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-24 0:27
 */
public interface GroupService {
    int add(GroupBean group);

    int addMember(int group, int user);

    int delete(int id);

    int update(GroupBean group);

    List<GroupBean> findByUserId(int id);

    List<GroupBean> find();

    GroupBean findOne(int id);

    int count();
}
