package com.xhu.bill.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 结算
 *
 * @author user17
 * @version 1.0
 * @date 2019-9-19 22:42
 */
@Data
public class SettleBean {
    private String id;
    private Long sumTime;
    private Long start;
    private Long end;
    private List<Map<String, SumBean>> maps;
}

@Data
class SumBean {
    /**
     * 支出
     */
    private BigDecimal expense;
    /**
     * 消费
     */
    private BigDecimal consume;

    /**
     * 小计
     */
    private BigDecimal total;
}
