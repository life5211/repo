package com.hnf.repository;

import com.hnf.bean.Teacher;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends GraphRepository<Teacher> {
}
