package cn.edu.xhu.phy.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @version 1.0
 * @date 2020/11/20 23:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_attr")
public class CxAttrBase extends Model<CxAttrBase> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("cx_code")
    private String cxCode;

    @TableField("cx_name")
    private String cxName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
