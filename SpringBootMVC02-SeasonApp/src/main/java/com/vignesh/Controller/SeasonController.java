package com.vignesh.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vignesh.Service.ISeasonService;

@Controller
public class SeasonController {
	
	@Autowired
	ISeasonService service;
	
	@RequestMapping("/")
	public String showHome() {
		return "welcome";
	}
	
	@RequestMapping("/season")
	public String showSeason(Map<String, String> map) {
		map.put("season", service.getSeason());
		return "season";	
	}

}
