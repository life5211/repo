package com.xhu.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author user17
 */
@SpringBootApplication
@EnableSwagger2
public class BillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillApplication.class, args);
    }

}
