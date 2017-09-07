package com.castle.docs.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.castle.docs.entity.Admin;
import com.castle.docs.security.AdminDetailsService.CurrentUserDetails;

@Component
public class AuthenticationAuditorAware implements AuditorAware<Admin> {

	@Override
	public Admin getCurrentAuditor() {
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
