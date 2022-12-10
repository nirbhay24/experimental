package com.example.redisOperations.dao;

import com.example.redisOperations.model.Employee;

import java.util.Map;

public interface IEmployeeDao {

    void saveEmployee(Employee emp);
    Employee getOneEmployee(Integer id);
    void updateEmployee(Employee emp);
    Map<Integer, Employee> getAllEmployees();
    void deleteEmployee(Integer id);
    void saveAllEmployees(Map<Integer, Employee> map);

}