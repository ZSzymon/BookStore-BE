package com.assigment.bookstore.paypal.token;

import com.assigment.bookstore.person.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<OrderModel, String> {

}
