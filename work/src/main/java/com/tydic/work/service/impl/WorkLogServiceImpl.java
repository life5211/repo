package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.WorkLogDao;
import com.tydic.work.entity.WorkLog;
import com.tydic.work.service.WorkLogService;
import org.springframework.stereotype.Service;

/**
 * (WorkLog)表服务实现类
 *
 * @author makejava
 * @since 2021-01-18 16:54:38
 */
@Service("workLogService")
public class WorkLogServiceImpl extends ServiceImpl<WorkLogDao, WorkLog> implements WorkLogService {

}