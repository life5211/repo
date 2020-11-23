package cn.edu.xhu.phy.base.service.impl;

import cn.edu.xhu.phy.base.entity.CxAttrBase;
import cn.edu.xhu.phy.base.mapper.CxAttrBaseMapper;
import cn.edu.xhu.phy.base.service.CxAttrBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @date 2020/11/21 2:27
 */
@Service("cxAttrBaseService")
public class CxAttrBaseServiceImpl
        extends ServiceImpl<CxAttrBaseMapper, CxAttrBase> implements CxAttrBaseService {
}
