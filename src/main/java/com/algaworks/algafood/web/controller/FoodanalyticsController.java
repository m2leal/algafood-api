package com.algaworks.algafood.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodanalyticsController {
	
	@GetMapping(path = "/foodanalytics")
	public String authorized() {
		return "pages/foodanalytics";
	}
	
}
