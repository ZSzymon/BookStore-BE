package com.assigment.bookstore.bookOrder.models;

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
public class BookOrderStatus {

    @Id
    private String id;
    private EBookOrderStatus orderStatus;
    private String name;

    @PersistenceConstructor
    public BookOrderStatus(String id, EBookOrderStatus orderStatus, String name) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.name = name;
    }
}
