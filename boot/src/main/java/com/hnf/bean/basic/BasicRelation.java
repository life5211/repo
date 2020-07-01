package com.hnf.bean.basic;

import org.neo4j.ogm.annotation.*;

import java.util.Date;
import java.util.Objects;

@RelationshipEntity(type = "DEFAULT")
public class BasicRelation {
    @GraphId
    private Long id;

    @StartNode
    private BasicNode source;

    @EndNode
    private BasicNode target;

    @Property
    private String relationName = "";

    @Property
    private Long added = new Date().getTime();

    public BasicRelation(BasicNode source, BasicNode target) {
        this.source = source;
        this.target = target;
    }

    public BasicRelation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicRelation that = (BasicRelation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(relationName, that.relationName) &&
                Objects.equals(added, that.added);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, target, relationName, added);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BasicNode getSource() {
        return source;
    }

    public void setSource(BasicNode source) {
        this.source = source;
    }

    public BasicNode getTarget() {
        return target;
    }

    public void setTarget(BasicNode target) {
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
