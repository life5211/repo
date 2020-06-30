package com.xhu.bill.mapper;

import com.xhu.bill.bean.GroupBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 20:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    public void add() {
        GroupBean groupBean = new GroupBean();
        groupBean.setName("name");
        groupMapper.add(groupBean);
        System.out.println(groupBean.getId());
    }

    @Test
    public void addMember() {
        groupMapper.addMember(4, 2);
    }

    @Test
    public void delete() {
        groupMapper.delete(3);
        groupMapper.delete(4);
    }

    @Test
    public void update() {
        GroupBean bean = new GroupBean();
        bean.setId(2);
        bean.setName("update");
        groupMapper.update(bean);
    }

    @Test
    public void find() {
        System.out.println(groupMapper.find());
    }

    @Test
    public void findOne() {
        System.out.println(groupMapper.findOne(1));
    }

    @Test
    public void count() {
        System.out.println(groupMapper.count());
    }
}