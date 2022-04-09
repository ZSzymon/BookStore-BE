package com.assigment.bookstore.book;

import com.assigment.bookstore.person.models.Person;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    //In mongodb in id is by default String.
    @Id
    private String id;
    private String title;

    @DocumentReference(lazy = true)
    private Person author;
    @DocumentReference(lazy = true)
    private Person publisher;

    @PersistenceConstructor
    public Book(String title, Person author, Person publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
