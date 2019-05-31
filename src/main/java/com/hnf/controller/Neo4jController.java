package com.hnf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hnf.bean.Student;
import com.hnf.bean.Teacher;
import com.hnf.bean.UserNode;
import com.hnf.bean.basic.BasicRelation;
import com.hnf.repository.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import io.fredia.femicro.graph.entity.UserNode;
//import io.fredia.femicro.graph.service.Neo4jService;

@RestController
public class Neo4jController {

    @Autowired
    private com.hnf.service.Neo4jService Neo4jService;
    @Autowired
    StudentDao studentDao;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BasicNodeDao basicNodeDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    BasicRelationshipDao basicRelationshipDao;

    @GetMapping("/getR")
    public Iterable<BasicRelation> getR(){
        return basicRelationshipDao.findAll();
    }

    @RequestMapping(path = "/addStu", method = RequestMethod.GET)
    public Iterable<Student> addStu() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setName(UUID.randomUUID().toString());
            student.setSex(UUID.randomUUID().toString());
            students.add(student);
        }
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Teacher teacher = new Teacher();
            teacher.setName(UUID.randomUUID().toString());
            teacher.setTel(UUID.randomUUID().toString());
            teachers.add(teacher);
        }
        teacherDao.save(teachers);
        studentDao.save(students);

        BasicRelation relation = new BasicRelation(students.get(0), teachers.get(0));
        relation.setRelationName("师生关系");
        basicRelationshipDao.save(relation);
        BasicRelation relation2 = new BasicRelation(students.get(1), teachers.get(1));
        relation2.setRelationName("师生关系");
        basicRelationshipDao.save(relation2);
        BasicRelation relation3 = new BasicRelation(students.get(1), teachers.get(0));
        relation3.setRelationName("师生关系");
        basicRelationshipDao.save(relation3);
        BasicRelation relation4 = new BasicRelation(teachers.get(0), students.get(0));
        relation4.setRelationName("师生关系");
        basicRelationshipDao.save(relation4);

        return studentDao.findAll();
    }

    //创建400个node
    @RequestMapping(path = "/addUserNode", method = RequestMethod.GET)
    public String addUserNode() {
        int i = 0;
        do {
            UserNode user = new UserNode();
            user.setAge(RandomUtils.nextInt(15, 40));
            user.setName("Fredia" + RandomUtils.nextInt(1, 1000));
            user.setUserId(UUID.randomUUID().toString());
            user.setNodeId(RandomUtils.nextLong(1L, 999L));
            Neo4jService.addUser(user);
            i += 1;
        } while (i < 400);

        return "ok";
    }

    @RequestMapping(path = "/getUserNodeList", method = RequestMethod.GET)
    public List<UserNode> getUserNodeList() {
        userRepository.deleteAll();
        return Neo4jService.getUserNodeList();
    }
}