package com.assigment.bookstore.order.models;

import com.assigment.bookstore.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private List<Book> bookList;
    private String description;

    @PersistenceConstructor
    public OrderDto(List<Book> bookList, String description) {
        this.bookList = bookList;
        this.description = description;
    }

    public OrderDto(Order order) {
        this.bookList = order.getOrderList();
    }
}
