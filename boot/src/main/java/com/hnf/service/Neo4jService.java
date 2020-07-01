package com.hnf.service;

import java.util.List;

import com.hnf.repository.UserRepository;
import com.hnf.bean.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import io.fredia.femicro.graph.entity.UserNode;
//import io.fredia.femicro.graph.repository.UserRelationRepository;
//import io.fredia.femicro.graph.repository.UserRepository;

@Service
public class Neo4jService {

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private UserRelationRepository userRelationRepository;

    public int addUser(UserNode userNode) {
        userRepository.addUserNodeList(userNode.getName(), userNode.getAge());
        return 1;
    }
    public  List<UserNode> getUserNodeList() {
        return (List<UserNode>) userRepository.findAll();
    }

    public void deleteByAge(int age) {
        userRepository.deleteAllByAge(age);
    }
    Iterable<UserNode> findAllByName(String name){
        return userRepository.findAllByName(name);
    }
}