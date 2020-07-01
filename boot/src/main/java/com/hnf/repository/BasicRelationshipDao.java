package com.hnf.repository;

import com.hnf.bean.basic.BasicRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BasicRelationshipDao extends Neo4jRepository<BasicRelation,Long> {
}
