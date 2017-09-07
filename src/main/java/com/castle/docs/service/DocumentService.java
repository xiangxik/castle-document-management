package com.castle.docs.service;

import org.springframework.stereotype.Service;

import com.castle.docs.entity.Document;
import com.castle.repo.jpa.EntityService;

@Service
public class DocumentService extends EntityService<Document, Long> {

}
