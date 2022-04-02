package com.assigment.bookstore.book;

import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

@Data
public class BookDTO implements Serializable {

    private String title;
    private PersonDTO author;
    private PersonDTO publisher;

    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.author = new PersonDTO(book.getAuthor());
        this.publisher = new PersonDTO(book.getPublisher());
    }

    @PersistenceConstructor
    public BookDTO(String title, PersonDTO author, PersonDTO publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }
}
