package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.PermissionDao;
import com.tydic.work.entity.Permission;
import com.tydic.work.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * permission表(Permission)表服务实现类
 *
 * @author EasyCode
 * @since 2021-07-23 14:34:32
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

}
