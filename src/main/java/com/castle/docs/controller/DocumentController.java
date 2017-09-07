package com.castle.docs.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.castle.docs.entity.Document;
import com.castle.docs.support.mvc.ConsoleEntityController;
import com.querydsl.core.types.Predicate;

@Controller
@RequestMapping("/console/document")
public class DocumentController extends ConsoleEntityController<Document, Long> {

	@Override
	public Page<Document> doPage(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
		return super.doInternalPage(predicate, pageable);
	}

}
