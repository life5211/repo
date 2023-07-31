package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.RoleDao;
import com.tydic.work.entity.Role;
import com.tydic.work.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表(Role)表服务实现类
 *
 * @author EasyCode
 * @since 2021-07-23 14:34:31
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}
