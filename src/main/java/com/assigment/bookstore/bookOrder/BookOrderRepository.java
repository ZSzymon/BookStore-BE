package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.bookOrder.models.BookOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookOrderRepository extends MongoRepository<BookOrder, String> {
    List<BookOrder> findAllByClientEmail(String email);
    Optional<BookOrder> findByPayPalOrderId(String id);
    void removeOrderByClientEmail(String email);
    void removeAllByClientEmail(String email);


}

