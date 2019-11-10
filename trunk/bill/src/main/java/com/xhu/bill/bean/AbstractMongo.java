package com.xhu.bill.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-18 22:37
 */
@Data
@Accessors(chain = true)
public abstract class AbstractMongo {
    /**
     * mongoDB 主键
     */
    protected String id;
    /**
     * 创建者
     */
    protected String createBy;
    /**
     * 创建时间
     */
    protected Long createTime;
    /**
     * 是否删除
     */
    protected int isDel;
}
