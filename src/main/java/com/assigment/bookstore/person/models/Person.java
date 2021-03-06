package com.assigment.bookstore.person.models;

import com.assigment.bookstore.cart.Cart;
import com.assigment.bookstore.securityJwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Person {
    //In mongodb in id is by default String.
    @Id
    private String id;
    private String firstName;
    private String secondName;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();

    @DocumentReference(lazy = true)
    @JsonIgnore
    private User user;

    @DocumentReference(lazy = true)
    @JsonIgnore
    private Cart cart;
    //For deserialisation purposes Person must have a zero-arg constructor.
    public Person(){}

    @PersistenceConstructor
    public Person(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                '}';
    }

    /**
     * Annotation PersistenceConstructor is needed to mapping object when retrieved from db.
     */
    @org.springframework.data.annotation.PersistenceConstructor
    public Person(String id, String firstName, String secondName, String email,
                  Gender gender, LocalDateTime created) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.created = LocalDateTime.now();

    }
    @Builder
    public Person(String firstName, String secondName, String email,
                  Gender gender) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
    }
    public Person(Person p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
    }
    @Builder
    public Person(String firstName, String secondName, String email, Gender gender, User user) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.user = user;
    }

    public Person(String email, User user) {
        this.email = email;
        this.user = user;
    }


}
