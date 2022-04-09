package com.assigment.bookstore.order;

import com.assigment.bookstore.order.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByClientEmail(String email);
    void removeOrderByClientEmail(String email);
}

