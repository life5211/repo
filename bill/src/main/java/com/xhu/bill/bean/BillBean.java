package com.xhu.bill.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 19:02
 */
@Data
public class BillBean {

    private String id;
    /**
     * 资方
     */
    private Map<String, String> investor;
    /**
     * 消费者
     */
    private List<Map<String, String>> consumers;

    /**
     * 时间
     */
    private Long recordTime;
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
