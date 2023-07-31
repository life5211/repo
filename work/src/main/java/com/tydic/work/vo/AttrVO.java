package com.tydic.work.vo;

import com.tydic.work.entity.Attr;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @version 1.0
 * @date 2021/1/21 10:57
 */
@Data
@Accessors(chain = true)
public class AttrVO {
    private Attr label;
    private List<Attr> values;
}
