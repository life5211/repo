package com.xhu.bill.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 19:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BillBean extends AbstractMongo {

    /**
     * 资方
     */
    private Map<String, String> investor;

    /**
     * 消费者
     */
    private Map<String, String> consumers;

    /**
     * 消费时间
     */
    private Long speedTime;

    /**
     * 消费金额
     */
    private String amount;

    /**
     * 备注
     */
    private String desc;

    /**
     * 成员组
     */
    private Integer group;

}
