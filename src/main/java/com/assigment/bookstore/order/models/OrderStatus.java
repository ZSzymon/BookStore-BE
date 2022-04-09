package com.assigment.bookstore.order.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
@NoArgsConstructor
public class OrderStatus {

    @Id
    private String id;
    private EOrderStatus orderStatus;
    private String name;

    @PersistenceConstructor
    public OrderStatus(String id, EOrderStatus orderStatus, String name) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.name = name;
    }
}
