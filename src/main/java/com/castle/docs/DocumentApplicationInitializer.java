package com.castle.docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.castle.docs.entity.Admin;
import com.castle.docs.service.AdminService;
import com.castle.integration.core.ApplicationInitializer;

@Component
@Order(0)
public class DocumentApplicationInitializer extends ApplicationInitializer {

	@Autowired
	private AdminService adminService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onRootContextRefreshed() {
		super.onRootContextRefreshed();

		if (adminService.count() == 0) {
			Admin superAdmin = adminService.initDomain();
			superAdmin.setUsername("admin");
			superAdmin.setPassword(passwordEncoder.encode("asd123"));
			superAdmin.setName("管理员");
			adminService.save(superAdmin);
		}

	}
}
