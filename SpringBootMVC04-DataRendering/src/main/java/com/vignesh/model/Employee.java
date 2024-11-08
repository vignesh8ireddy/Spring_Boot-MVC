package com.vignesh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private Integer eno;
	private String ename;
	private String eaddrs;
	private  Double salary;

}
