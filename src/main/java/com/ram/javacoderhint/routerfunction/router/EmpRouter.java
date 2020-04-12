package com.ram.javacoderhint.routerfunction.router;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ram.javacoderhint.routerfunction.handler.EmpHandler;

@Configuration
public class EmpRouter {

	@Autowired
	EmpHandler empHandler;

/*	
	@Bean
	public RouterFunction<ServerResponse> route1() {
		return RouterFunctions.route(POST("/employees").and(contentType(APPLICATION_JSON)), empHandler::createEmp)
				.andRoute(GET("/employees").and(accept(APPLICATION_JSON)), empHandler::listEmp)
				.andRoute(GET("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler::getEmpById)
				.andRoute(PUT("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler::updateEmp)
				.andRoute(DELETE("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler::deleteEmp)
				.andRoute(GET("/employees/events/stream").and(accept(APPLICATION_JSON)), empHandler::empEvents);
	} */

	@Bean
	RouterFunction<ServerResponse> route() {
		return RouterFunctions.nest(path("/employee"),
				RouterFunctions.nest(
						accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
						RouterFunctions.route(GET("/"), empHandler::listEmp)
								.andRoute(method(HttpMethod.POST), empHandler::createEmp)
								.andRoute(GET("/events"), empHandler::empEvents).andNest(path("/{id}"),
										RouterFunctions.route(method(HttpMethod.GET), empHandler::getEmpById)
												.andRoute(method(HttpMethod.PUT), empHandler::updateEmp)
												.andRoute(method(HttpMethod.DELETE), empHandler::deleteEmp))));
	}

}
