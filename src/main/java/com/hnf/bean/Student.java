package com.hnf.bean;

import com.hnf.bean.basic.BasicNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Objects;

@NodeEntity
public class Student {

    @GraphId
    private Long id;
    private String name;
    private String tel;
    private String sex;

    public Student() {
    }

    public Student(Long id, String name, String tel, String sex) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) &&
                name.equals(student.name) &&
                tel.equals(student.tel) &&
                sex.equals(student.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tel, sex);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"tel\":\"")
                .append(tel).append('\"');
        sb.append(",\"sex\":\"")
                .append(sex).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
