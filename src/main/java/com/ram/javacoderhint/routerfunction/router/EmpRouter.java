package com.ram.javacoderhint.routerfunction.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ram.javacoderhint.routerfunction.handler.EmpHandler;

@Configuration
public class EmpRouter {

	@Autowired
	EmpHandler empHandler;

    
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(POST("/employees").and(contentType(APPLICATION_JSON)), empHandler::createEmp)
                .andRoute(GET("/employees").and(accept(APPLICATION_JSON)), empHandler::listEmp)
                .andRoute(GET("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler::getEmpById)
                .andRoute(PUT("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler :: updateEmp)
                .andRoute(DELETE("/employees/{id}").and(accept(APPLICATION_JSON)), empHandler :: deleteEmp)
                .andRoute(GET("/employees/events/stream").and(accept(APPLICATION_JSON)), empHandler :: empEvents);        
    }    

}
