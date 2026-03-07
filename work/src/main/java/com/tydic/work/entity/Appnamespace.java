package com.tydic.work.entity;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用namespace定义(Appnamespace)表实体类
 *
 * @author EasyCode
 * @since 2021-07-27 09:29:13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("appnamespace")
@KeySequence(value = "seq_appnamespace_id")
public class Appnamespace extends Model<Appnamespace> {

    private static final long serialVersionUID = -16276220737198107L;

    /**
     * 自增主键
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * namespace名字，注意，需要全局唯一
     */
    @TableField(value = "Name")
    private String name;

    /**
     * app id
     */
    @TableField(value = "AppId")
    private String appid;

    /**
     * namespace的format类型
     */
    @TableField(value = "Format")
    private String format;

    /**
     * namespace是否为公共
     */
    @TableField(value = "IsPublic")
    private Object ispublic;

    /**
     * 注释
     */
    @TableField(value = "Comment")
    private String comment;

    /**
     * 1: deleted, 0: normal
     */
    @TableField(value = "IsDeleted")
    private Object isdeleted;

    /**
     * 创建人邮箱前缀
     */
    @TableField(value = "DataChange_CreatedBy")
    private String datachangeCreatedby;

    /**
     * 创建时间
     */
    @TableField(value = "DataChange_CreatedTime")
    private Date datachangeCreatedtime;

    /**
     * 最后修改人邮箱前缀
     */
    @TableField(value = "DataChange_LastModifiedBy")
    private String datachangeLastmodifiedby;

    /**
     * 最后修改时间
     */
    @TableField(value = "DataChange_LastTime")
    private Date datachangeLasttime;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 获取主键值
     * 自增主键
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
