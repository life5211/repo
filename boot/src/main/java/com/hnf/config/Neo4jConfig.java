package com.hnf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.hnf.repository")
// 激活SDN隐式事务
@EnableTransactionManagement
public class Neo4jConfig {
}