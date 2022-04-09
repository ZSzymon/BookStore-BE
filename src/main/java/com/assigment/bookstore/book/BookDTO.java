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

    public BookDTO(Book book) {
        this.title = book == null ? "" : book.getTitle();
        this.author = (book == null) ? null : new PersonDTO(book.getAuthor());
        this.publisher =(book == null) ? null : new PersonDTO(book.getPublisher());
    }

    @PersistenceConstructor
    public BookDTO(String title, PersonDTO author, PersonDTO publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

}
