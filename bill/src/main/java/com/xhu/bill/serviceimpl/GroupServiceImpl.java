package com.xhu.bill.serviceimpl;

import com.xhu.bill.bean.GroupBean;
import com.xhu.bill.mapper.GroupMapper;
import com.xhu.bill.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-29 17:31
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public int add(GroupBean group) {
        return 0;
    }

    @Override
    public int addMember(int group, int user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(GroupBean group) {
        return 0;
    }

    @Override
    public List<GroupBean> findByUserId(int id) {
        return groupMapper.findByUserId(id);
    }

    @Override
    public List<GroupBean> find() {
        return null;
    }

    @Override
    public GroupBean findOne(int id) {
        return groupMapper.findOne(id);
    }

    @Override
    public int count() {
        return 0;
    }
}
