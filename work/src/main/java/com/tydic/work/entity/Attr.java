package com.tydic.work.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Attr)表实体类
 *
 * @author makejava
 * @since 2021-01-18 16:49:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Attr extends Model<Attr> {

    private static final long serialVersionUID = 8451041276296184712L;
    @TableId
    private Integer id;

    private String code;

    private String text;

    private String note;

    private String labelCode;

    private Short exp;

    private Short del;



    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}