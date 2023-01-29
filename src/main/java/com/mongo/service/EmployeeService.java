package com.mongo.service;

import com.mongo.entity.Employee;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final MongoTemplate mongoTemplate;

    public EmployeeService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Employee> getAllEmployees() {
        return mongoTemplate.findAll(Employee.class);
    }

    public Employee getEmployeeByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        return mongoTemplate.findOne(query, Employee.class);
    }

    public List<Employee> getAllActiveEmployees() {
        Query query = new Query();
        query.addCriteria(Criteria.where("isActive").is(true));
        return mongoTemplate.find(query, Employee.class);
    }

    public Employee addEmployee(Employee employee) {
        return mongoTemplate.insert(employee);
    }

    public UpdateResult activateEmployee(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.fields().include("isActive");
        Update update = new Update();
        update.set("isActive", true);
        return mongoTemplate.updateFirst(query, update, Employee.class);
    }

    public DeleteResult deleteEmployee(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.remove(query, Employee.class);
    }
}
