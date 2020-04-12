package com.ram.javacoderhint.routerfunction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ram.javacoderhint.routerfunction.model.Employee;
import com.ram.javacoderhint.routerfunction.repository.EmpRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class RouterfunctionRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouterfunctionRestApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(EmpRepository repository) {
		return args -> {
			Flux<Employee> empFlux = Flux
					.just(new Employee(null, "Ram", 32), new Employee(null, "Shya", 32))
					.flatMap(repository::save);

			empFlux.thenMany(repository.findAll()).subscribe(System.out::println);
		};

	}	

}
