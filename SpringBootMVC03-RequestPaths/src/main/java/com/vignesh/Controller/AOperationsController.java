package com.vignesh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-operations")
public class AOperationsController {
	
	  @GetMapping("/all")
      public  String showData1() {
		  System.out.println("TestOperationsController.showData1()");
    	  return "display2";
	  }

}
