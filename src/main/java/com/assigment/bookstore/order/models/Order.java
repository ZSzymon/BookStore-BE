package com.assigment.bookstore.order.models;

import com.assigment.bookstore.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    private String id;
    @DocumentReference(lazy = true)
    private List<Book> orderList;
    private String clientEmail;
    private String description;

    @Builder
    @PersistenceConstructor
    public Order(String id, List<Book> orderList, String clientEmail, String description) {
        this.id = id;
        this.orderList = orderList;
        this.clientEmail = clientEmail;
        this.description = description;
    }
}
