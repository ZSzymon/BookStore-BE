package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.bookOrder.models.BookOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookOrderRepository extends MongoRepository<BookOrder, String> {
    List<BookOrder> findAllByClientEmail(String email);
    void removeOrderByClientEmail(String email);
    void removeAllByClientEmail(String email);


}

