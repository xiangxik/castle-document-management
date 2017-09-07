package com.castle.docs.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.castle.docs.entity.Admin;
import com.castle.docs.entity.QAdmin;
import com.castle.repo.jpa.EntityRepository;

public interface AdminRepository extends EntityRepository<Admin, Long>, QuerydslBinderCustomizer<QAdmin> {

	Admin findByUsername(String username);

	@Override
	default void customize(QuerydslBindings bindings, QAdmin root) {
		bindings.bind(root.username).first((path, value) -> path.contains(value));
		bindings.bind(root.name).first((path, value) -> path.contains(value));

		bindings.excluding(root.password);
	}
}
