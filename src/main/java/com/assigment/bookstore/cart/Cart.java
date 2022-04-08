package com.assigment.bookstore.cart;


import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.securityJwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    private String id;
    private String personEmail;
    private List<Book> books;

    @PersistenceConstructor
    public Cart(List<Book> books) {
        this.books = books;
    }

    public Cart(String email) {
        this.personEmail = email;
        this.books = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "personEmail='" + personEmail + '\'' +
                '}';
    }
}
