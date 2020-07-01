package com.hnf.repository;

import com.hnf.bean.basic.BasicNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BasicNodeDao extends Neo4jRepository<BasicNode,Long> {
}
