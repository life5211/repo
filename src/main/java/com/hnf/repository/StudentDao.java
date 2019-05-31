package com.hnf.repository;

import com.hnf.bean.Student;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends GraphRepository<Student> {
}
