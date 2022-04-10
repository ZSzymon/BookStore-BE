package com.assigment.bookstore.bookOrder.models;

import com.assigment.bookstore.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BookOrder {

    @Id
    private String id;
    @DocumentReference(lazy = true)
    private List<Book> orderList;
    private String clientEmail;
    private String description;
    private EBookOrderStatus orderStatus;

    public BookOrder(List<Book> books, String email, String description) {
        this.orderList = books;
        this.clientEmail = email;
        this.description = description;
        this.orderStatus = EBookOrderStatus.CREATED;
    }

    public double getTotalOrderAmount(){
        return orderList.stream().map(Book::getPrice)
                .reduce(Double.valueOf("0"), Double::sum);
    }
}
