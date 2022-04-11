package com.assigment.bookstore.paypal.token;

import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.paypal.http.Headers;
import com.paypal.orders.Order;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Document("OrderModels")
public class OrderModel {

    @Id
    private String id;
    private String token;
    private Headers headers;
    private int statusCode;
    private String result;

    @DocumentReference(lazy = true)
    private BookOrder bookOrder;

//    @Indexed(direction = IndexDirection.DESCENDING)
//    private final LocalDateTime created = LocalDateTime.now();

    public OrderModel(String token, Headers headers, int statusCode, String result, BookOrder bookOrder) {
        this.token = token;
        this.headers = headers;
        this.statusCode = statusCode;
        this.result = result;
        this.bookOrder = bookOrder;
    }
}
