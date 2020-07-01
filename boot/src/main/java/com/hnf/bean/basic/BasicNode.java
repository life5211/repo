package com.hnf.bean.basic;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;
import java.util.Set;

@NodeEntity
public class BasicNode {
    @GraphId
    private Long id;

    @Property
    private String nodeName;

    @Property
    private Long added = new Date().getTime();

    @Relationship()
    private Set<BasicRelation> outGoing;

    @Relationship(direction = Relationship.INCOMING)
    private Set<BasicRelation> inComing;

    public BasicNode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getAdded() {
        return added;
    }

    public void setAdded(Long added) {
        this.added = added;
    }

    public Set<BasicRelation> getOutGoing() {
        return outGoing;
    }

    public void setOutGoing(Set<BasicRelation> outGoing) {
        this.outGoing = outGoing;
    }

    public Set<BasicRelation> getInComing() {
        return inComing;
    }

    public void setInComing(Set<BasicRelation> inComing) {
        this.inComing = inComing;
    }
}
