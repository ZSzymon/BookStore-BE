package com.assigment.bookstore.bookOrder.models;

import com.assigment.bookstore.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookOrderDto {
    private List<String> bookList;
    private String description;

    @PersistenceConstructor
    public BookOrderDto(List<String> bookList, String description) {
        this.bookList = bookList;
        this.description = description;
    }

    public BookOrderDto(BookOrder bookOrder) {
        this.bookList = bookOrder.getOrderList().stream().map(Book::getId).toList();
    }
}
