package com.hnf.bean;

import com.hnf.bean.basic.BasicNode;
import com.hnf.bean.basic.BasicRelation;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

@RelationshipEntity(type = "Relationship")
public class Relationship {
    private String name = "测试关系";
    @GraphId
    private Long id;

    @StartNode
    private Teacher source;

    @EndNode
    private Student target;

    @Property
    private String relationName = "";

    @Property
    private Long added = new Date().getTime();

    public Relationship(Teacher source, Student target) {
        this.source = source;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getSource() {
        return source;
    }

    public void setSource(Teacher source) {
        this.source = source;
    }

    public Student getTarget() {
        return target;
    }

    public void setTarget(Student target) {
        this.target = target;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public Long getAdded() {
        return added;
    }

    public void setAdded(Long added) {
        this.added = added;
    }
}
