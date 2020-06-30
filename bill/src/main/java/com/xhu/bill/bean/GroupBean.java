package com.xhu.bill.bean;

import lombok.Data;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:49
 */
@Data
public class GroupBean {

    private Integer id;

    private String name;

    private List<String> users;
}
