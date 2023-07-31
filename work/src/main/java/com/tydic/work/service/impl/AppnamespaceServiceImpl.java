package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.AppnamespaceDao;
import com.tydic.work.dao.PermissionDao;
import com.tydic.work.dao.RoleDao;
import com.tydic.work.dao.RolepermissionDao;
import com.tydic.work.dao.UserroleDao;
import com.tydic.work.entity.Appnamespace;
import com.tydic.work.entity.Permission;
import com.tydic.work.entity.Role;
import com.tydic.work.entity.Rolepermission;
import com.tydic.work.entity.Userrole;
import com.tydic.work.service.AppnamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 应用namespace定义(Appnamespace)表服务实现类
 *
 * @author EasyCode
 * @since 2021-07-23 14:34:28
 */
@Service("appnamespaceService")
public class AppnamespaceServiceImpl extends ServiceImpl<AppnamespaceDao, Appnamespace> implements AppnamespaceService {


    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RolepermissionDao rolepermissionDao;

    @Autowired
    UserroleDao userroleDao;


    @Override
    public boolean insert(String nameSpace) {
        String appId = "hubei";
        Appnamespace appnamespace = new Appnamespace().setName(nameSpace).setAppid(appId).setComment("注释").setFormat("properties")
                .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());

        this.baseMapper.insert(appnamespace);
        String[] envs = new String[]{"DEV", "TEST", ""};

        for (String env : envs) {
            if (!env.isEmpty()) {
                env = "+" + env;
            }
            Permission p1 = new Permission().setPermissiontype("ModifyNamespace").setTargetid(String.format("%s+%s%s", appId, nameSpace, env))
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            Permission p2 = new Permission().setPermissiontype("ReleaseNamespace").setTargetid(String.format("%s+%s%s", appId, nameSpace, env))
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            permissionDao.insert(p1);
            permissionDao.insert(p2);


            Role r1 = new Role().setRolename(String.format("ModifyNamespace+%s+%s%s", appId, nameSpace, env))
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            Role r2 = new Role().setRolename(String.format("ReleaseNamespace+%s+%s%s", appId, nameSpace, env))
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            roleDao.insert(r1);
            roleDao.insert(r2);

            Rolepermission rp1 = new Rolepermission().setRoleid(r1.getId()).setPermissionid(p1.getId())
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            Rolepermission rp2 = new Rolepermission().setRoleid(r2.getId()).setPermissionid(p2.getId())
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            rolepermissionDao.insert(rp1);
            rolepermissionDao.insert(rp2);

            Userrole ur1 = new Userrole().setUserid("apollo").setRoleid(r1.getId())
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            Userrole ur2 = new Userrole().setUserid("apollo").setRoleid(r2.getId())
                    .setDatachangeCreatedby("apollo").setDatachangeCreatedtime(new Date())
                    .setDatachangeLastmodifiedby("apollo").setDatachangeLasttime(new Date());
            userroleDao.insert(ur1);
            userroleDao.insert(ur2);


        }
        return true;

    }
}
