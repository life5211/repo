package com.xhu.bill.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:46
 */
@Data
public class UserBean extends AbstractMysql {

    private String username;

    private String name;

    @JsonIgnore
    private String password;
}
