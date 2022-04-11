package com.assigment.bookstore.paypal.token;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<OrderModel, String> {

}
