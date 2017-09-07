package com.castle.docs.support.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.castle.docs.entity.Admin;
import com.castle.docs.security.AdminDetailsService.CurrentUserDetails;

public abstract class DefaultController extends com.castle.integration.webapp.mvc.WebController {

	protected Admin getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof CurrentUserDetails) {
			return ((CurrentUserDetails) principal).getCustomUser();
		}
		return null;
	}
}
