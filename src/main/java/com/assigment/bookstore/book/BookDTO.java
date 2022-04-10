package com.assigment.bookstore.book;

import com.assigment.bookstore.person.models.PersonDTO;
import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;

import java.io.Serializable;

@Data
public class BookDTO implements Serializable {

    private String title;
    private PersonDTO author;
    private PersonDTO publisher;
    private double price;
    public BookDTO(Book book) {
        this.title = book == null ? "" : book.getTitle();
        this.author = (book == null) ? null : new PersonDTO(book.getAuthor());
        this.publisher =(book == null) ? null : new PersonDTO(book.getPublisher());
        this.price = (book == null) ? 0.0 :book.getPrice();
    }

    @PersistenceConstructor
    public BookDTO(String title, PersonDTO author, PersonDTO publisher, double price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

}
