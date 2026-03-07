package com.tydic.work.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (WorkLog)表实体类
 *
 * @author makejava
 * @since 2021-01-18 16:54:35
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkLog extends Model<WorkLog> {

    private static final long serialVersionUID = -7215826135261666697L;

    private Integer id;

    private String userCode;

    private String userName;

    private Date logDate;

    private Date workDate;

    private Integer workLength;

    private String workContent;

    private String workVersion;

    private String workNote;

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