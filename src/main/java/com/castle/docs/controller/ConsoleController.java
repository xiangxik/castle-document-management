package com.castle.docs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.castle.docs.support.mvc.DefaultController;

@Controller
@RequestMapping("/console")
public class ConsoleController extends DefaultController {

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show() {
		return "/console/index";
	}
	
}
