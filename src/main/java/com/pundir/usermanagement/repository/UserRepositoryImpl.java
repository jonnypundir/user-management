package com.pundir.usermanagement.repository;

import com.pundir.usermanagement.entities.Role;
import com.pundir.usermanagement.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl {
  @Autowired
  MongoTemplate mongoTemplate;

  public List<User> getNonAdminUser(String role){
    Query query = new Query();
    query.addCriteria(Criteria.where("name").ne(role));
    System.out.println("Query "+query);
    List<Role> role1 = mongoTemplate.find(query,Role.class);
    System.out.println(role1);
    Query query1 = new Query();
    Set<Role> roleSet = new HashSet<>(role1);
    query1.addCriteria(Criteria.where("roles").in(roleSet));
    return mongoTemplate.find(query1,User.class);
  }
}
