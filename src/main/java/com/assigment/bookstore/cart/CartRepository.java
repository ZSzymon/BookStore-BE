package com.assigment.bookstore.cart;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByPersonEmail(String email);
    void deleteByPersonEmail(String email);

}