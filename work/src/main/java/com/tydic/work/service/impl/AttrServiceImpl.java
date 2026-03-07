package com.tydic.work.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tydic.work.dao.AttrDao;
import com.tydic.work.entity.Attr;
import com.tydic.work.service.AttrService;
import com.tydic.work.vo.AttrVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Attr)表服务实现类
 *
 * @author makejava
 * @since 2021-01-18 16:49:11
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, Attr> implements AttrService {

    @Override
    public AttrVO getAttrs(String code) {
        Attr attr = this.getBaseMapper().selectOne(Wrappers.lambdaQuery(new Attr()).eq(Attr::getCode, code));
        List<Attr> attrs = this.getBaseMapper().selectList(Wrappers.lambdaQuery(new Attr()).eq(Attr::getLabelCode, code));
        return new AttrVO().setLabel(attr).setValues(attrs);
    }

}