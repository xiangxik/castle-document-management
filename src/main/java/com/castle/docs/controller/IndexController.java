package com.castle.docs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.castle.docs.entity.Document;
import com.castle.docs.service.DocumentService;
import com.castle.docs.support.mvc.DefaultController;
import com.querydsl.core.types.Predicate;

@Controller
public class IndexController extends DefaultController {

	@Autowired
	private DocumentService documentService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(@QuerydslPredicate(root = Document.class) Predicate predicate, Pageable pageable, Model model) {
		model.addAttribute("docs", documentService.findAll(predicate, pageable));
		return "/index";
	}

	@RequestMapping(value = "/doc/{id}", method = RequestMethod.GET)
	public String doc(@PathVariable("id") Document doc, Model model) {
		model.addAttribute("doc", doc);
		return "/doc";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/login";
	}

}
