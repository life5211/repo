package com.xhu.bill.mapper;

import com.xhu.bill.bean.GroupBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:57
 */
@Mapper
public interface GroupMapper {

    int add(GroupBean group);

    int addMember(int group, int user);

    int delete(int id);

    int update(GroupBean group);

    List<GroupBean> findByUserId(int id);

    List<GroupBean> find();

    GroupBean findOne(int id);

    int count();
}
