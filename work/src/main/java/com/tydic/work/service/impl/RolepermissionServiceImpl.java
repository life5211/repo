package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.RolepermissionDao;
import com.tydic.work.entity.Rolepermission;
import com.tydic.work.service.RolepermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色和权限的绑定表(Rolepermission)表服务实现类
 *
 * @author EasyCode
 * @since 2021-07-23 14:34:30
 */
@Service("rolepermissionService")
public class RolepermissionServiceImpl extends ServiceImpl<RolepermissionDao, Rolepermission> implements RolepermissionService {

}
