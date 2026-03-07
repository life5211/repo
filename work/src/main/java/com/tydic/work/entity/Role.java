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
 * 角色表(Role)表实体类
 *
 * @author EasyCode
 * @since 2021-07-27 09:29:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("role")
@KeySequence(value = "seq_role_id")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 718559234467593797L;

    /**
     * 自增Id
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * Role name
     */
    @TableField(value = "RoleName")
    private String rolename;

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
     * 自增Id
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
