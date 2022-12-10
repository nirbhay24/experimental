package com.example.redisOperations.runner;

import java.util.Map;

import com.example.redisOperations.dao.IEmployeeDao;
import com.example.redisOperations.dao.IHitCount;
import com.example.redisOperations.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisOpertionsRunner implements CommandLineRunner {

    @Autowired
    private IEmployeeDao empDao;

    @Autowired
    private IHitCount hitCount;

    @Override
    public void run(String... args) throws Exception {

        //saving one employee
        crudOperations();

        System.out.println("Going to do hyperloglog operations");
        String PAGE2 = "PAGE2";
        hitCount.putToLog(PAGE2, "231.237.83.182");
        hitCount.putToLog(PAGE2, "62.247.0.61");
        hitCount.putToLog(PAGE2, "215.22.104.179");
        hitCount.putToLog(PAGE2, "185.72.101.237", "19.162.98.102", "57.221.211.127", "123.181.3.164", "246.125.179.169");
        System.out.println(hitCount.getCount(PAGE2));


    }

    private void crudOperations() {
        empDao.saveEmployee(new Employee(500, "Emp0", 2150.0));

        //saving multiple employees
        empDao.saveAllEmployees(
                Map.of(501, new Employee(501, "Emp1", 2396.0),
                        502, new Employee(502, "Emp2", 2499.5),
                        503, new Employee(503, "Emp4", 2324.75)
                )
        );

        //modifying employee with empId 503
        empDao.updateEmployee(new Employee(503, "Emp3", 2325.25));

        //deleting employee with empID 500
        empDao.deleteEmployee(500);

        //retrieving all employees
        empDao.getAllEmployees().forEach((k, v) -> System.out.println(k + " : " + v));

        //retrieving employee with empID 501
        System.out.println("Emp details for 501 : " + empDao.getOneEmployee(501));
    }
}