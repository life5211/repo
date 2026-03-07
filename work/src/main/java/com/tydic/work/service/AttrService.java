package com.tydic.work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tydic.work.entity.Attr;
import com.tydic.work.vo.AttrVO;

import java.util.List;

/**
 * (Attr)表服务接口
 *
 * @author makejava
 * @since 2021-01-18 16:49:10
 */
public interface AttrService extends IService<Attr> {
    AttrVO getAttrs(String code);
}