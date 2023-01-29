package com.mongo.controller;

import com.mongo.entity.Employee;
import com.mongo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class HomeController {

    private final EmployeeService employeeService;

    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<Employee>> getAllActiveEmployees() {
        return ResponseEntity.ok(employeeService.getAllActiveEmployees());
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.getEmployeeByName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @GetMapping("/activate/{name}")
    public ResponseEntity<String> activateEmployee(@PathVariable String name) {
        long count = employeeService.activateEmployee(name).getModifiedCount();
        if(count == 1) {
            return ResponseEntity.ok(name + " activated successfully");
        } else {
            return new ResponseEntity<>(name + " user not found.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{name}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String name) {
        long count = employeeService.deleteEmployee(name).getDeletedCount();
        if(count == 1) {
            return ResponseEntity.ok(name + " deleted successfully");
        } else {
            return new ResponseEntity<>(name + " user not found.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
