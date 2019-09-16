package com.xhu.bill.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 19:02
 */
@Data
public class BillBean {


    /**
     * 资方
     */
    private String investor;
    /**
     * 消费者
     */
    private String[] consumers;
    /**
     * 时间
     */
    private Long recordTime = System.currentTimeMillis();
    /**
     * 消费时间
     */
    private Date speedTime;

    /**
     * 消费金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String desc;


}
