package com.hnf.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author xhz
 * @version 1.0
 * @date 2019/8/20 18:29
 */
@Configuration
//@ConfigurationProperties(prefix = "mongo.dbs")
public class MongoConfig {
    @Value("${mongo.dbs.hosts}")
    List<Map> hosts;
    String db;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public List<Map> getHosts() {
        return hosts;
    }

    public void setHosts(List<Map> hosts) {
        this.hosts = hosts;
    }
}
