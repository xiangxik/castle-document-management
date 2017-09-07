package com.castle.docs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.dialect.springdata.util.Strings;

import com.castle.docs.entity.Admin;
import com.castle.docs.support.mvc.ConsoleEntityController;
import com.querydsl.core.types.Predicate;

@Controller
@RequestMapping("/console/admin")
public class AdminController extends ConsoleEntityController<Admin, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Page<Admin> doPage(Predicate predicate, @PageableDefault(sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
		return super.doInternalPage(predicate, pageable);
	}

	@Override
	protected void onValidate(Admin entity, BindingResult bindingResult) {
		if (entity.isNew() && Strings.isEmpty(getParameter("rawPassword"))) {
			bindingResult.addError(new FieldError("entity", "password", "新用户密码不能为空"));
		}
	}

	@Override
	protected void onBeforeSave(Admin entity) {
		String rawPassword = getParameter("rawPassword");
		if (!Strings.isEmpty(rawPassword)) {
			entity.setPassword(passwordEncoder.encode(rawPassword));
		}
	}
}
