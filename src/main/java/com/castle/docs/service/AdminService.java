package com.castle.docs.service;

import org.springframework.stereotype.Service;

import com.castle.docs.entity.Admin;
import com.castle.repo.jpa.EntityService;

@Service
public class AdminService extends EntityService<Admin, Long> {

}
