package com.vignesh.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vignesh.model.Student;


@Controller
public class StudentOperationsController {
	
	@GetMapping("/")  //for welcome page
	public  String   showHomePage() {
		return "welcome";
	}
	
	@GetMapping("/register")
	public  String  showStudenRegistrationPage() {   //for form launching
		return  "student_register_form";
	}
	
	@PostMapping("/register")
	public  String   processStudentRegistrationForm(Map<String,Object> map, @ModelAttribute("stud") Student st) {   
		//for form submission
		System.out.println("model class obj data ::"+st);
		//keep  Model class obj data as the model attribute
		map.put("studInfo", st);
		//return LVN
		return "show_result";
	}

}
