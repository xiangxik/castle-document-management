package com.castle.docs.support.mvc;

import java.io.Serializable;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.castle.docs.entity.Admin;

public abstract class ConsoleEntityController<T, I extends Serializable> extends EntityController<T, I> {

	@ModelAttribute("currentUser")
	public Admin setCurrentUserAttr() {
		return super.getCurrentUser();
	}
}
