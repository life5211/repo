package com.xhu.bill.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 17:56
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoClient getMongoClient() {
        return new MongoClient();
    }

}
