package com.assigment.bookstore.book;

import com.assigment.bookstore.person.Gender;
import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.securityJwt.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    //In mongodb in id is by default String.
    @Id
    private String id;
    private String title;
    @DBRef
    private Person author;
    @DBRef
    private Person publisher;

    @PersistenceConstructor
    public Book(String title, Person author, Person publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

}
