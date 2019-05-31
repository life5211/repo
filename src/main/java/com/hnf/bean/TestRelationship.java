package com.hnf.bean;

import com.hnf.bean.basic.BasicNode;
import com.hnf.bean.basic.BasicRelation;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "TestRelationship")
public class TestRelationship extends BasicRelation {
    private String name = "测试关系";

    public TestRelationship() {
    }

    public <T extends BasicNode, E extends BasicNode> TestRelationship(T source, E target) {
        super(source, target);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
