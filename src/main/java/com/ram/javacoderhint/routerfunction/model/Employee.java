package com.ram.javacoderhint.routerfunction.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "employees")
public class Employee {

	@Id
	private String id;
	
    @NotNull
    @Size(min = 2, max = 5)
	private String name;
    
    @NotNull
	private int age;

}
