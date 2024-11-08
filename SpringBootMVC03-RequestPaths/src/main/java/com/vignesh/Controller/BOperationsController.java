package com.vignesh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home-operations")//global mapping
public class BOperationsController {

	/*  @RequestMapping(value={"/report1","/report2","/report3"})
    public   String  showReport() {
	  System.out.println("ShowHomeController.showReport()");
       return  "display";	
    }*/

	@RequestMapping("/home")
	public String showHome() {
		System.out.println("ShowHomeController.showHome()");
		return "welcome";
	}
	
	/*
	//@RequestMapping(value="/report1",method = RequestMethod.GET)
	@GetMapping
	public   String   showReport1() {
		System.out.println("ShowHomeController.showReport1()");
		return "display";
	}
	
	//@RequestMapping(value="/report2",method = RequestMethod.POST)
	@PostMapping
	public   String   showReport2() {
		System.out.println("ShowHomeController.showReport2()");
		return "display1";
	}*/
	
	   @GetMapping("/all")
	  public  String showData() {
		   System.out.println("ShowHomeController.showData()");
		  return "forward:test-operations/all";
	  }
	
}
