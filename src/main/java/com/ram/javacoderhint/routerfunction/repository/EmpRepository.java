package com.ram.javacoderhint.routerfunction.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.ram.javacoderhint.routerfunction.model.Employee;

public interface EmpRepository  extends ReactiveMongoRepository<Employee, String>{

}
