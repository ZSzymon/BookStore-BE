package com.assigment.bookstore.securityJwt.repository;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);


}
