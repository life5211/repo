package cn.edu.xhu.phy.service.impl;

import cn.edu.xhu.phy.entity.CxAttrEntity;
import cn.edu.xhu.phy.mapper.CxAttrMapper;
import cn.edu.xhu.phy.service.CxAttrService;
import cn.edu.xhu.phy.base.mapper.CxAttrBaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @date 2020/11/21 18:44
 */
@Service
public class CxAttrServiceImpl extends ServiceImpl<CxAttrMapper, CxAttrEntity> implements CxAttrService {
    // @Autowired
    // private CxAttrBaseService cxAttrBaseService;

    @Autowired
    private CxAttrBaseMapper cxAttrBaseMapper;

    @Autowired
    private CxAttrMapper cxAttrMapper;


}
