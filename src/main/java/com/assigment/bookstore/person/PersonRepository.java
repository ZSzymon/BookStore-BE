package com.assigment.bookstore.person;

import com.assigment.bookstore.person.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByEmail(String email);

    Optional<Person> removePersonByEmail(String email);

}