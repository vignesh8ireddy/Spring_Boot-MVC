package com.vignesh.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.vignesh.model.Employee;

@Controller
public class ShowHomeController {

	/*
	@GetMapping("/")
	 public  String   showData(Map<String,Object> map) {
		// keep the data in model attributes  (simple values)
		   map.put("name", "raja");
		   map.put("age",30);
		   //return LVN
		   return "welcome";
	}
	*/
	
	/*
	 @GetMapping("/")
	 public  String   showData(Map<String,Object> map) {
		 //keep the data in model attributes  (collections and arrays)
		    map.put("nickNames",new String[] {"king","raja","maharaja"});
		    map.put("friends",List.of("shiva","jani","albert","kohli"));
		    map.put("phoneNumbers",Set.of(988888123,986666633,43565655));
		    map.put("idDetails",Map.of("aadhar",98908908,"voterId",535354,"panNo",5435454));
		   //return LVN
		   return "welcome";
	}
	*/
	
	
	/*
	 @GetMapping("/")
	 public  String   showData(Map<String,Object> map) {
		 //keep the data in model attributes  (Model class object)
		       Employee emp=new Employee(101,"raja","hyd",900000.0);
		       map.put("empData", emp);
		   //return LVN
		   return "welcome";
	}
	*/
	
	
	
	@GetMapping("/")
	 public  String   showData(Map<String,Object> map) {
		 //keep the data in model attributes  (List of Model class objects)
		       Employee emp1=new Employee(101,"raja","hyd",900000.0);
		       Employee emp2=new Employee(102,"rajesh","vizag",900000.0);
		       Employee emp3=new Employee(103,"tarun","delhi",910000.0);
		       List<Employee> empsList=List.of(emp1,emp2,emp3);
		       
		       //add model attribute 
		       map.put("empsList", empsList);
		   
		   //return LVN
		   return "welcome";
	}
	
	
}
