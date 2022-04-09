package com.assigment.bookstore.person.models;

import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;


@Data
public class PersonDTO {

    private String firstName;
    private String secondName;
    private String email;

    @PersistenceConstructor
    public PersonDTO(Person p) {
        this.firstName = p == null ? " " : p.getFirstName();
        this.secondName = p == null ? " " : p.getSecondName();
        this.email = p == null ? " " : p.getEmail();
    }

    @PersistenceConstructor
    public PersonDTO(String firstName, String secondName, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }
}
