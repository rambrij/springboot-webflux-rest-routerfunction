package com.ram.javacoderhint.routerfunction.handler;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ram.javacoderhint.routerfunction.model.Employee;
import com.ram.javacoderhint.routerfunction.model.EmployeeEvent;
import com.ram.javacoderhint.routerfunction.repository.EmpRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class EmpHandler {

	@Autowired
	private EmpRepository empRepository;
	

	public Mono<ServerResponse> createEmp(ServerRequest request) {
		Mono<Employee> empMono = request.bodyToMono(Employee.class);
		
		return empMono
				.flatMap(emp -> ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(empRepository.save(emp), Employee.class));
	}


	public Mono<ServerResponse> updateEmp(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Employee> existingEmpMono = empRepository.findById(id);
		Mono<Employee> empMono = request.bodyToMono(Employee.class);

		Mono<ServerResponse> notFound = ServerResponse.notFound().build();

		return empMono.zipWith(existingEmpMono,
				(emp, existingEmp) -> new Employee(existingEmp.getId(), emp.getName(), emp.getAge()))
				.flatMap(emp -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(empRepository.save(emp), Employee.class)
				.switchIfEmpty(notFound));
	}	
	public Mono<ServerResponse> listEmp(ServerRequest request) {
		Flux<Employee> empList = empRepository.findAll();
		log.info("inside listEmp method");
		return ServerResponse.ok().
				contentType(MediaType.APPLICATION_JSON)
				.body(empList, Employee.class);
	}

	public Mono<ServerResponse> getEmpById(ServerRequest request) {
		log.info("inside getEmpById method");

		String id = request.pathVariable("id");		
		Mono<Employee> empMono = empRepository.findById(id);
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();

		return empMono
				.flatMap(emp -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON).bodyValue(emp))
				.switchIfEmpty(notFound);
	}

	public Mono<ServerResponse> deleteEmp(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Employee> empMono = empRepository.findById(id);
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		return empMono.
				flatMap(emp -> ServerResponse.ok()
						.build(empRepository.delete(emp)))
				.switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> empEvents(ServerRequest serverRequest) {
		Flux<EmployeeEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
				.map(val -> new EmployeeEvent(val, "Emp Events"));

		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(eventFlux, EmployeeEvent.class);
	}
}