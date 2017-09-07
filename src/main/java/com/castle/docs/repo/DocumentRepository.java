package com.castle.docs.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.castle.docs.entity.Document;
import com.castle.docs.entity.QDocument;
import com.castle.repo.jpa.EntityRepository;

public interface DocumentRepository extends EntityRepository<Document, Long>, QuerydslBinderCustomizer<QDocument> {

	@Override
	default void customize(QuerydslBindings bindings, QDocument root) {
		bindings.bind(root.title, root.catalog).first((path, value) -> path.contains(value));
	}
}
